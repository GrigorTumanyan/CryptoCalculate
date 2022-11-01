package am.cryptoCalculate.controller.impl;

import am.cryptoCalculate.controller.CryptoController;
import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.service.CryptoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("v1/crypto")
public class CryptoControllerImpl implements CryptoController {

    private final CryptoService cryptoService;

    public CryptoControllerImpl(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    @PostMapping
    public ResponseEntity<CryptoDto> add() {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CryptoDto> getById(Long id) {
        CryptoDto byId = cryptoService.getById(id);
        System.out.println(byId);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<CryptoDto>> getAll() {
        return null;
    }

    @Override
    @GetMapping("/min")
    public ResponseEntity<CryptoDto> getMinPriceBySymbol(String symbol) {
        CryptoDto min = cryptoService.getMinPriceBySymbol(symbol);
        return new ResponseEntity<>(min, HttpStatus.OK);
    }

    @Override
    @GetMapping("/oldest")
    public ResponseEntity<CryptoDto> getOldestBySymbol(String symbol) {
        CryptoDto oldest = cryptoService.getOldestBySymbol(symbol);
        return new ResponseEntity<>(oldest, HttpStatus.OK);
    }

    @Override
    @GetMapping("/newest")
    public ResponseEntity<CryptoDto> getNewestBySymbol(String symbol) {
        CryptoDto newest =  cryptoService.getNewestBySymbol(symbol);
        return new ResponseEntity<>(newest, HttpStatus.OK);
    }

    @Override
    @GetMapping("/max")
    public ResponseEntity<CryptoDto> getMaxPriceBySymbol(String symbol) {
        CryptoDto max = cryptoService.getMaxBySymbol(symbol);
        return new ResponseEntity<>(max, HttpStatus.OK);
    }

    @Override
    @GetMapping("/min/search")
    public ResponseEntity<CryptoDto> getMinPriceBySymbolAndGivenDate(String symbol, LocalDateTime start, LocalDateTime end) {
        CryptoDto minPriceBySymbolLastMonth = cryptoService.getMinPriceBySymbolAndGivenDate(symbol, start, end);
        return new ResponseEntity<>(minPriceBySymbolLastMonth, HttpStatus.OK);
    }
}
