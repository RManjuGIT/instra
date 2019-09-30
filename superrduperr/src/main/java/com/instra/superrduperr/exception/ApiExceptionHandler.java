package com.instra.superrduperr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorItem> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorItem errors = new ErrorItem();
		errors.setMessage(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(SuperrDuperrException.class)
    public ResponseEntity<ErrorItem> handleSuperrDuperrException(SuperrDuperrException ex) {
		ErrorItem errors = new ErrorItem();
		errors.setMessage(ex.getMessage());
		errors.setCode(ex.getCode());
        return new ResponseEntity<>(errors, HttpStatus.EXPECTATION_FAILED);
    }
 
}
