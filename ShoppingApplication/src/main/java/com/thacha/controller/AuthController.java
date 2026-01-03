package com.thacha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thacha.dto.JwtResponse;
import com.thacha.dto.LoginRequest;
import com.thacha.dto.SignupRequest;
import com.thacha.dto.SignupResponse;
import com.thacha.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> createUser(@Valid @RequestBody SignupRequest signUpRequest) {

		log.info("Signup request received for email: {}", signUpRequest.getEmail());

		SignupResponse signupResponse = userService.registerUser(signUpRequest);

		log.info("User registered successfully for email: {}", signupResponse.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		
		log.info("Login request received for email: {}", loginRequest.getEmail());
		
		JwtResponse jwtResponse = userService.login(loginRequest);
		
		log.info("User login completed successfully for email : {}", loginRequest.getEmail());
		
		return ResponseEntity.ok(jwtResponse);
		
	}

}
