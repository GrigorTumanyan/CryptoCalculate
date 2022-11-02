package am.cryptoCalculate.config;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.dto.CryptoReadCSVDto;
import am.cryptoCalculate.mapper.CryptoMapper;
import am.cryptoCalculate.model.Crypto;
import org.springframework.batch.item.ItemProcessor;

public class CryptoProcessor implements ItemProcessor<CryptoReadCSVDto, Crypto> {
    @Override
    public Crypto process(CryptoReadCSVDto cryptoReadCSVDto) {
        return CryptoMapper.fromCryptoReadCSVDtoToCrypto(cryptoReadCSVDto);
    }
}
