package com.bibum_server.domain.exception;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e){
        log.info("Error occurred, {}", e);

        ErrorResponse errorResponse = new ErrorResponse(e.getCode(), e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(Integer.parseInt(e.getCode())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleNoSuchElementException(MethodArgumentNotValidException e){
        log.info("Method argument not valid!!, {}", e);

        ErrorResponse errorResponse = new ErrorResponse("400", "잘못된 요청입니다.");

        e.getFieldErrors().forEach(error -> {
            errorResponse.addValidation(error.getField(), error.getDefaultMessage());
        });

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e){
        log.error("Unexpected Error!!, {}", e);

        Sentry.captureException(e);

        return new ErrorResponse("500", "서버 오류가 발생했습니다.");
    }
}
