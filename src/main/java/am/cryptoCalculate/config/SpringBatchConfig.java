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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    public final CryptoRepository cryptoRepository;

    @Value("${input.files.location}")
    private String inputResources;

    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CryptoRepository cryptoRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.cryptoRepository = cryptoRepository;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MultiResourceItemReader<CryptoReadCSVDto> multiReader() throws IOException {
        MultiResourceItemReader<CryptoReadCSVDto> multiReader = new MultiResourceItemReader<>();
        Resource[] convert;
        try (Stream<Path> list = Files.list(Path.of(inputResources))) {
            convert = list.map(PathResource::new).toArray(Resource[]::new);
        }
        multiReader.setResources(convert);
        multiReader.setDelegate(reader());
        return multiReader;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FlatFileItemReader<CryptoReadCSVDto> reader() {
        FlatFileItemReader<CryptoReadCSVDto> reader = new FlatFileItemReader<>();
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
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CryptoProcessor processor() {
        return new CryptoProcessor(cryptoRepository);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RepositoryItemWriter<Crypto> writer() {
        RepositoryItemWriter<Crypto> writerItem = new RepositoryItemWriter<>();
        writerItem.setRepository(cryptoRepository);
        writerItem.setMethodName("save");
        return writerItem;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Step firstStep() throws IOException {
        return stepBuilderFactory.get("first-step-csv").<CryptoReadCSVDto, Crypto>chunk(10)
                .reader(multiReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Job job() throws IOException {
        return jobBuilderFactory.get("first-job-csv")
                .flow(firstStep()).end().build();
    }

}
