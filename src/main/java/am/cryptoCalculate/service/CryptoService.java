package am.cryptoCalculate.service;

import am.cryptoCalculate.dto.CryptoDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface CryptoService {

    CryptoDto getById(Long id);
    CryptoDto getOldestBySymbol(String symbol);

    CryptoDto getNewestBySymbol(String symbol);

    CryptoDto getMinPriceBySymbol(String symbol);

    CryptoDto getMaxBySymbol(String symbol);

    Page<CryptoDto> getCryptos(String symbol, int pageSize, int offset, String direction);

    CryptoDto getMinPriceBySymbolAndGivenDate(String symbol, LocalDateTime start, LocalDateTime end);

    CryptoDto readCSVFile(String path);

}
