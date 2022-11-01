package am.cryptoCalculate.repository;

import am.cryptoCalculate.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    String DURATION = " and timestamp BETWEEN now() - interval '30 day' and now())";

    @Query(value = "SELECT * FROM crypto WHERE price = (SELECT min(price) FROM crypto WHERE symbol = :symbol" + DURATION +
            " ORDER BY timestamp ASC", nativeQuery = true)
    List<Crypto> getMinBySymbol(String symbol);

    @Query(value = "SELECT * FROM crypto WHERE timestamp = (SELECT min (timestamp) FROM crypto WHERE symbol = :symbol" + DURATION,
            nativeQuery = true)
    List<Crypto> getOldestBySymbol(String symbol);

    @Query(value = "SELECT * FROM crypto WHERE timestamp = (SELECT max (timestamp) FROM crypto WHERE symbol = :symbol" + DURATION,
            nativeQuery = true)
    List<Crypto> getNewestBySymbol(String symbol);

    @Query(value = "SELECT * FROM crypto WHERE price = (SELECT min(price) from crypto where symbol = ?1" +
            " and timestamp between ?2 and ?3 ) ORDER BY timestamp ASC", nativeQuery = true)
    List<Crypto> getMinBySymbolAndDuration(String symbol, LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM crypto WHERE price = (SELECT max(price) FROM crypto WHERE symbol = :symbol" + DURATION +
            " ORDER BY timestamp ASC", nativeQuery = true)
    List<Crypto> getMaxBySymbol(String symbol);
}
