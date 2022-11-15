package am.cryptoCalculate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptoDto {
    private Long id;
    private LocalDateTime timestamp;
    private String symbol;
    private double price;

    public CryptoDto(LocalDateTime timestamp, String symbol, double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }
}
