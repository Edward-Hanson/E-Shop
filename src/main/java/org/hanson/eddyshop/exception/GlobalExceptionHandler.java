package org.hanson.eddyshop.exception;

import org.hanson.eddyshop.exception.customizedExceptions.CartItemRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.CartRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.CategoryRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.ImageRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.OrderRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.ProductRelatedExceptions;
import org.hanson.eddyshop.exception.customizedExceptions.UserRelatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductRelatedExceptions.class, CategoryRelatedException.class, ImageRelatedException.class,
            CartRelatedException.class, CartItemRelatedException.class, OrderRelatedException.class, UserRelatedException.class})
    public ResponseEntity<ErrorDto> handleCustomizedExceptions(Exception e) {
        ErrorDto error = ErrorDto.builder()
                .message(List.of(e.getMessage()))
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorDto errorDto = new ErrorDto();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        errorDto.setCode(HttpStatus.BAD_REQUEST.value());
        errorDto.setMessage(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler({SQLException.class, IOException.class})
    public ResponseEntity<ErrorDto> handleSQLException(Exception e) {
        ErrorDto error = ErrorDto.builder()
                .message(List.of(e.getMessage()))
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception e) {
        ErrorDto error = ErrorDto.builder()
                .message(List.of(e.getMessage()))
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
