package com.hoangminh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exc){

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                notFound,
                exc.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exc){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                badRequest,
                exc.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(FormatException.class)
    public ResponseEntity<Object> handleFormatException(FormatException exc){

        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                serverError,
                exc.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, serverError);
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<Object> handleUnknownException(UnknownException exc){

        HttpStatus unknownError = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                unknownError,
                exc.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, unknownError);
    }

    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<Object> handleEmptyResultException(EmptyResultException exc){

        HttpStatus emptyResult = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                emptyResult,
                exc.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, emptyResult);
    }
}
