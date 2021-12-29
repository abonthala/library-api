package com.skb.course.apis.libraryapis.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.skb.course.apis.libraryapis.model.common.LibraryApiError;

@ControllerAdvice
public class LibraryControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryControllerExceptionHandler.class);
	
	@ExceptionHandler(LibraryResourceAlreadyExistsException.class)
	public ResponseEntity<LibraryApiError> handleLibraryResourceAlreadyExists(LibraryResourceAlreadyExistsException e)
	{
		return new ResponseEntity<>(new LibraryApiError(e.getMessage(), e.getTraceId(), e.getStatus()), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(LibraryResourceNotFoundException.class)
	public ResponseEntity<LibraryApiError> handleLibraryResourceNotFound(LibraryResourceNotFoundException e)
	{
		//logger.error("");
		return new ResponseEntity<>(new LibraryApiError(e.getMessage(), e.getTraceId(), e.getStatus()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(LibraryResourceBadRequestException.class)
	public ResponseEntity<LibraryApiError> handleLibraryResourceBadRequest(LibraryResourceBadRequestException e)
	{
		return new ResponseEntity<>(new LibraryApiError(e.getMessage(), e.getTraceId(), e.getStatus()), HttpStatus.BAD_REQUEST);
	}

}
