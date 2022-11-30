package am.cryptoCalculate.service.impl;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.exception.ParamInvalidException;
import am.cryptoCalculate.exception.RecordNotFoundException;
import am.cryptoCalculate.mapper.CryptoMapper;
import am.cryptoCalculate.model.Crypto;
import am.cryptoCalculate.repository.CryptoRepository;
import am.cryptoCalculate.service.CryptoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    public CryptoServiceImpl(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public CryptoDto getById(Long id) {
        Crypto byId = cryptoRepository.getById(id);
        return CryptoMapper.fromCryptoToCryptoDto(byId);
    }

    @Override
    public CryptoDto getOldestBySymbol(String symbol) {
        List<Crypto> oldest = cryptoRepository.getOldestBySymbol(symbol);
        if (oldest.isEmpty()) {
            throw new RecordNotFoundException("Crypto : " + symbol + " is not exist");
        }
        return CryptoMapper.fromCryptoToCryptoDto(oldest.get(0));
    }

    @Override
    public CryptoDto getNewestBySymbol(String symbol) {
        List<Crypto> newest = cryptoRepository.getNewestBySymbol(symbol);
        if (newest.isEmpty()) {
            throw new RecordNotFoundException("Crypto : " + symbol + " is not exist");
        }
        return CryptoMapper.fromCryptoToCryptoDto(newest.get(0));
    }

    @Override
    public CryptoDto getMinPriceBySymbol(String symbol) {
        List<Crypto> crypto = cryptoRepository.getMinBySymbol(symbol);
        if (crypto.isEmpty()) {
            throw new RecordNotFoundException(symbol + " crypto does not exist");
        }
        return CryptoMapper.fromCryptoToCryptoDto(crypto.get(0));
    }

    @Override
    public CryptoDto getMaxBySymbol(String symbol) {
        List<Crypto> max = cryptoRepository.getMaxBySymbol(symbol);
        return CryptoMapper.fromCryptoToCryptoDto(max.get(0));
    }

    @Override
    public Page<CryptoDto> getCryptos(String symbol, int pageSize, int offset, String direction) {
        return null;
    }

    @Override
    public CryptoDto getMinPriceBySymbolAndGivenDate(String symbol, LocalDateTime start, LocalDateTime end) {
        List<Crypto> cryptoList = cryptoRepository.getMinBySymbolAndDuration(symbol, start, end);
        if (cryptoList.isEmpty()) {
            throw new RecordNotFoundException(symbol + " crypto does not exist");
        }
        return CryptoMapper.fromCryptoToCryptoDto(cryptoList.get(0));
    }

    @Override
    public BigDecimal getHighestNormalizedRangeByDate(LocalDateTime start) {
        LocalDateTime endDate = start.plusDays(1);
        List<Crypto> cryptoList = cryptoRepository.getCryptosByOneDay(start, endDate);
        if (cryptoList.isEmpty()) {
            throw new ParamInvalidException("Date is not correct or doesn't found the record with specify day");
        }
        BigDecimal maxPrice = cryptoList.get(cryptoList.size() - 1).getPrice();
        BigDecimal minPrice = cryptoList.get(0).getPrice();
        if (!BigDecimal.ZERO.equals(minPrice)) {
            return (maxPrice.subtract(minPrice)).divide(minPrice);
        }
        throw new RuntimeException("System couldn't count the normalized range");
    }
}
