package com.sos.exception.handler;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.sos.exception.ResourceNotFoundException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return ex.getMessage();
	}

	@ExceptionHandler(value = { ValidationException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String validationException(ValidationException ex, WebRequest request) {
		return ex.getMessage();
	}

}