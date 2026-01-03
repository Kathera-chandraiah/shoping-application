package com.thacha.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
	
	private String error;
	private int status;
	private String trace;
	private String path;
	private String message;
	private LocalDate timeStamp;
}
