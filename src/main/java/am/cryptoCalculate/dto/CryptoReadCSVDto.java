package am.cryptoCalculate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CryptoReadCSVDto {

    private long timestamp;

    private String symbol;

    private BigDecimal price;
}
