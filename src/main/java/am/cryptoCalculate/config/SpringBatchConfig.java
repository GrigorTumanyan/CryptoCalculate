package am.cryptoCalculate.config;

import am.cryptoCalculate.dto.CryptoReadCSVDto;
import am.cryptoCalculate.model.Crypto;
import am.cryptoCalculate.repository.CryptoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    public final CryptoRepository cryptoRepository;

    @Value("classpath:/input/*.csv")
    private Resource[] inputResources;

    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CryptoRepository cryptoRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.cryptoRepository = cryptoRepository;
    }
    @Bean
    public MultiResourceItemReader<CryptoReadCSVDto> multiReader(){
        MultiResourceItemReader<CryptoReadCSVDto> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(inputResources);
        multiReader.setDelegate(reader());
        return multiReader;
    }
    @Bean
    public FlatFileItemReader<CryptoReadCSVDto> reader(){
        FlatFileItemReader<CryptoReadCSVDto> reader = new FlatFileItemReader<>();
//        reader.setResource(new FileSystemResource("src/main/resources/BTC_values.csv"));
        reader.setName("csvReader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<CryptoReadCSVDto> lineMapper() {
        DefaultLineMapper<CryptoReadCSVDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("timestamp", "symbol", "price");

        BeanWrapperFieldSetMapper<CryptoReadCSVDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CryptoReadCSVDto.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CryptoProcessor processor(){
        return new CryptoProcessor();
    }
    @Bean
    public RepositoryItemWriter<Crypto> writer(){
        RepositoryItemWriter<Crypto> writerItem = new RepositoryItemWriter<>();
        writerItem.setRepository(cryptoRepository);
        writerItem.setMethodName("save");
        return writerItem;
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("first-step-csv").<CryptoReadCSVDto, Crypto>chunk(10)
                .reader(multiReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("first-job-csv")
                .flow(firstStep()).end().build();
    }

}
