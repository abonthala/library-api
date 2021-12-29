package com.skb.course.apis.libraryapis.exceptions;

import org.springframework.http.HttpStatus;

public class LibraryResourceBadRequestException extends Exception{
	
	private String traceId;
	private HttpStatus status;
	
	public LibraryResourceBadRequestException(String message, String traceId, HttpStatus status)
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
		return "LibraryResourceBadRequestException [traceId=" + traceId + ", status=" + status + "]";
	}
}
