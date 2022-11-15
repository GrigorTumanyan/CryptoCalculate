package am.cryptoCalculate.config;

import am.cryptoCalculate.dto.CryptoReadCSVDto;
import am.cryptoCalculate.mapper.CryptoMapper;
import am.cryptoCalculate.model.Crypto;
import am.cryptoCalculate.repository.CryptoRepository;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.List;

public class CryptoProcessor implements ItemProcessor<CryptoReadCSVDto, Crypto> {

    private final CryptoRepository cryptoRepository;

    public CryptoProcessor(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public Crypto process(CryptoReadCSVDto cryptoReadCSVDto) {
        LocalDateTime localDateTime = CryptoMapper.longToLocalDateTime(cryptoReadCSVDto.getTimestamp());
        List<Crypto> byTimestamp = cryptoRepository.getByTimestamp(localDateTime);
        for (Crypto crypto : byTimestamp) {
            if (crypto.getSymbol().equals(cryptoReadCSVDto.getSymbol())){
                return null;
            }
        }
        return CryptoMapper.fromCryptoReadCSVDtoToCrypto(cryptoReadCSVDto);
    }
}
