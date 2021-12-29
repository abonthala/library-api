package com.skb.course.apis.libraryapis.model.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LibraryApiError {
	
	private String errorMessage;
	private String traceId;
	private HttpStatus status;

}
