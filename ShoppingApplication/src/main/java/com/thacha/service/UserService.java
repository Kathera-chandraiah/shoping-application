package com.thacha.service;

import com.thacha.dto.JwtResponse;
import com.thacha.dto.LoginRequest;
import com.thacha.dto.SignupRequest;
import com.thacha.dto.SignupResponse;

public interface UserService {
	SignupResponse registerUser(SignupRequest signupRequest);
	JwtResponse login(LoginRequest loginRequest);
}
