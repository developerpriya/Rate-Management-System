package com.example.test.rate.exception;

import com.example.test.rate.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RateNotFound.class)
    public final ResponseEntity<Object> rateNotFound(RateNotFound ex, WebRequest request) {
        ErrorMessage error = new ErrorMessage("404", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorMessage error = new ErrorMessage("500","Internal server error. Please contact admin");
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage("500","Internal server error. Please contact admin");
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}