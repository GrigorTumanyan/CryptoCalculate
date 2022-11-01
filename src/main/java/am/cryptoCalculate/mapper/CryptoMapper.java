package am.cryptoCalculate.mapper;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.model.Crypto;

public class CryptoMapper {
    public static Crypto toCrypto(CryptoDto cryptoDto){
        return Crypto.builder()
                .timestamp(cryptoDto.getTimestamp())
                .symbol(cryptoDto.getSymbol())
                .price(cryptoDto.getPrice())
                .build();
    }

    public static CryptoDto toCryptoDto(Crypto crypto){
        return CryptoDto.builder()
                .id(crypto.getId())
                .timestamp(crypto.getTimestamp())
                .symbol(crypto.getSymbol())
                .price(crypto.getPrice())
                .build();
    }
}
