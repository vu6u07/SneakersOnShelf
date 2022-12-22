package com.sos.exception.handler;

import java.text.ParseException;

import javax.validation.ValidationException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
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
	
	@ExceptionHandler(value = { FileSizeLimitExceededException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String fileSizeLimitExceededException(FileSizeLimitExceededException ex, WebRequest request) {
		return "Dung lượng tải lên quá lớn.";
	}

	@ExceptionHandler(value = { ParseException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String parseException(ParseException ex, WebRequest request) {
		return "Dữ liệu không hợp lệ";
	}

}