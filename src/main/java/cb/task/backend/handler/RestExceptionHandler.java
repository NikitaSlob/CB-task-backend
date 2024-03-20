package cb.task.backend.handler;

import cb.task.backend.bean.ErrorResponse;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleExceptions(
            Exception ex, WebRequest request) {
        val bodyOfResponse = "Непредвиденная ошибка";
        log.error(bodyOfResponse, ex);
        val responseEntity = ErrorResponse.builder()
                .message(bodyOfResponse)
                .status(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseEntity.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            Exception ex, WebRequest request) {
        val bodyOfResponse = "Ошибка запроса: " + ex.getMessage();
        log.error(bodyOfResponse, ex);
        val responseEntity = ErrorResponse.builder()
                .message(bodyOfResponse)
                .status(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseEntity.build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        val bodyOfResponse = "Ошибка запроса: " + ex.getMessage();
        log.error(bodyOfResponse, ex);
        val responseEntity = ErrorResponse.builder()
                .message(bodyOfResponse)
                .status(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseEntity.build(), HttpStatus.BAD_REQUEST);
    }
}
