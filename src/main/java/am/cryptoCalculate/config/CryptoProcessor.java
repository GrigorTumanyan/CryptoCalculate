package am.cryptoCalculate.config;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.mapper.CryptoMapper;
import am.cryptoCalculate.model.Crypto;
import org.springframework.batch.item.ItemProcessor;

public class CryptoProcessor implements ItemProcessor<CryptoDto, Crypto> {
    @Override
    public Crypto process(CryptoDto cryptoDto) throws Exception {
        return CryptoMapper.toCrypto(cryptoDto);
    }
}
