package am.cryptoCalculate.service.impl;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.exception.RecordNotFoundException;
import am.cryptoCalculate.mapper.CryptoMapper;
import am.cryptoCalculate.model.Crypto;
import am.cryptoCalculate.repository.CryptoRepository;
import am.cryptoCalculate.service.CryptoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        return CryptoMapper.toCryptoDto(byId);
    }

    @Override
    public CryptoDto getOldestBySymbol(String symbol) {
        List<Crypto> oldest = cryptoRepository.getOldestBySymbol(symbol);
        if (oldest.isEmpty()) {
            throw new RecordNotFoundException("Crypto : " + symbol + " is not exist");
        }
        return CryptoMapper.toCryptoDto(oldest.get(0));
    }

    @Override
    public CryptoDto getNewestBySymbol(String symbol) {
        List<Crypto> newest = cryptoRepository.getNewestBySymbol(symbol);
        if (newest.isEmpty()) {
            throw new RecordNotFoundException("Crypto : " + symbol + " is not exist");
        }
        return CryptoMapper.toCryptoDto(newest.get(0));
    }

    @Override
    public CryptoDto getMinPriceBySymbol(String symbol) {
        List<Crypto> crypto = cryptoRepository.getMinBySymbol(symbol);
        if (crypto.isEmpty()) {
            throw new RecordNotFoundException(symbol + " crypto does not exist");
        }
        return CryptoMapper.toCryptoDto(crypto.get(0));
    }

    @Override
    public CryptoDto getMaxBySymbol(String symbol) {
        List<Crypto> max = cryptoRepository.getMaxBySymbol(symbol);
        return CryptoMapper.toCryptoDto(max.get(0));
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
        return CryptoMapper.toCryptoDto(cryptoList.get(0));
    }

    @Override
    public CryptoDto readCSVFile(String filePath) {

return null;


    }
}
