package am.cryptoCalculate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LocalDateTime now = LocalDateTime.now();

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception){
        ErrorResponse serverException = new ErrorResponse("500", "ServerException",
                exception.getMessage(), now);
        return new ResponseEntity<>(serverException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception){
        ErrorResponse not_found = new ErrorResponse("404", "NOT_FOUND", exception.getMessage(), now);
        return new ResponseEntity<>(not_found, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception){
        ErrorResponse conflict = new ErrorResponse("409", "CONFLICT", exception.getMessage(), now);
        return new ResponseEntity<>(conflict, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException exception){
        ErrorResponse forbidden = new ErrorResponse("403", "FORBIDDEN", exception.getMessage(), now);
        return new ResponseEntity<>(forbidden, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtExpiredException.class)
    public final ResponseEntity<ErrorResponse> handleJwtExpiredException(JwtExpiredException exception){
        ErrorResponse unauthorized = new ErrorResponse("401", "UNAUTHORIZED", exception.getMessage(), now);
        return new ResponseEntity<>(unauthorized, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ParamInvalidException.class)
    public final ResponseEntity<ErrorResponse> handleParamInvalidException(ParamInvalidException exception){
        ErrorResponse bad_request = new ErrorResponse("400", "BAD_REQUEST", exception.getMessage(), now);
        return new ResponseEntity<>(bad_request, HttpStatus.BAD_REQUEST);
    }
}
