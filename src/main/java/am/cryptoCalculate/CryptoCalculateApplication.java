package am.cryptoCalculate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Crypto API", version = "1.0", description = "Crypto exchange"),
        servers = {@Server(url = "http://localhost:8080/", description = "Main server v1")})
public class CryptoCalculateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCalculateApplication.class, args);
    }
}