package com.skb.course.apis.libraryapis.exceptions;

import org.springframework.http.HttpStatus;

public class LibraryResourceAlreadyExistsException extends RuntimeException {
	
	private String traceId;
	private HttpStatus status;
	
	public LibraryResourceAlreadyExistsException(String message, String traceId, 
												HttpStatus status)
	{
		super(message);
		this.traceId = traceId;
		this.status = status;
	}

	public String getTraceId() {
		return traceId;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "LibraryResourceAlreadyExistsException [traceId=" + traceId + ", status=" + status + "]";
	}

}
