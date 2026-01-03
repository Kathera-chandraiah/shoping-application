package com.thacha.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex,
			HttpServletRequest request) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		ErrorResponse error = ErrorResponse.builder().error(HttpStatus.CONFLICT.getReasonPhrase())
				.status(HttpStatus.CONFLICT.value()).trace(sw.toString()).message(ex.getMessage())
				.path(request.getServletPath()).timeStamp(LocalDate.now()).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(PasswordMisMatchException.class)
	public ResponseEntity<ErrorResponse> handlePasswordMisMatch(PasswordMisMatchException ex,HttpServletRequest request){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		ErrorResponse error = ErrorResponse.builder().error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.status(HttpStatus.BAD_REQUEST.value()).trace(sw.toString()).message(ex.getMessage())
				.path(request.getServletPath()).timeStamp(LocalDate.now()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

}
