package am.cryptoCalculate.mapper;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.dto.CryptoReadCSVDto;
import am.cryptoCalculate.model.Crypto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class CryptoMapper {
    public static Crypto fromCryptoDtoToCrypto(CryptoDto cryptoDto){
        return Crypto.builder()
                .timestamp(cryptoDto.getTimestamp())
                .symbol(cryptoDto.getSymbol())
                .price(cryptoDto.getPrice())
                .build();
    }

    public static CryptoDto fromCryptoToCryptoDto(Crypto crypto){
        return CryptoDto.builder()
                .id(crypto.getId())
                .timestamp(crypto.getTimestamp())
                .symbol(crypto.getSymbol())
                .price(crypto.getPrice())
                .build();
    }


    public static Crypto fromCryptoReadCSVDtoToCrypto(CryptoReadCSVDto cryptoReadCSVDto){

        return Crypto.builder()
                .symbol(cryptoReadCSVDto.getSymbol())
                .price(cryptoReadCSVDto.getPrice())
                .timestamp(longToLocalDateTime(cryptoReadCSVDto.getTimestamp()))
                .build();
    }

    public static LocalDateTime longToLocalDateTime(long timestamp){
        return new Timestamp(new Date(timestamp).getTime()).toLocalDateTime();
    }
}
