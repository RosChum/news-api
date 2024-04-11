package com.example.newsapi.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestControllerAdviceExecutor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        List<String> errorMessages = bindingResult.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        String errorMessage = String.join(", ", errorMessages);

        log.info("Ошибка при заполнении полей " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<HttpServletResponse> authenticationException(HttpServletResponse response) {
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    @ExceptionHandler(AccessRightsException.class)
    public ResponseEntity<ErrorResponse> authenticationException() {
        ErrorResponse errorResponse = new ErrorResponse("Нет прав на редактирование");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ErrorResponse> authenticationException(ContentNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
    }

    @ExceptionHandler({RefreshTokenException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> authenticationException(RuntimeException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}