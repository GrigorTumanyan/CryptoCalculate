package am.cryptoCalculate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtExpiredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JwtExpiredException(String exception) {
        super(exception);
    }
}
