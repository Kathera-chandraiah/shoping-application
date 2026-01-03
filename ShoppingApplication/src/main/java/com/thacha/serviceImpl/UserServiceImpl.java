package com.thacha.serviceImpl;

import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thacha.dto.JwtResponse;
import com.thacha.dto.LoginRequest;
import com.thacha.dto.SignupRequest;
import com.thacha.dto.SignupResponse;
import com.thacha.entity.User;
import com.thacha.exception.EmailAlreadyExistsException;
import com.thacha.exception.PasswordMisMatchException;
import com.thacha.filter.JwtService;
import com.thacha.mapper.UserMapper;
import com.thacha.repository.UserRepository;
import com.thacha.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	private final JwtService jwtService;

	@Override
	public SignupResponse registerUser(SignupRequest signupRequest) {

		log.info("Signup request for email: {}", signupRequest.getEmail());

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new EmailAlreadyExistsException(
					String.format("Email %s is Already exists!", signupRequest.getEmail()));
		}

		if (!signupRequest.getPassword().toLowerCase().equals(signupRequest.getConfirmPassword().toLowerCase())) {
			throw new PasswordMisMatchException(
					String.format("Confirm password %s is mismatched", signupRequest.getConfirmPassword()));
		}
		User user = userMapper.mapSignUpRequestToUser(signupRequest);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
		User savedUser = userRepository.save(user);
		SignupResponse signupResponse = userMapper.mapUserToSignupResponse(savedUser);

		log.info("User saved successfully in db for email: {}", savedUser.getEmail());

		return signupResponse;
	}

	@Override
	public JwtResponse login(LoginRequest loginRequest) {

		log.info("Login Request for email: {}", loginRequest.getEmail());

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword());
		System.out.println(auth);
		Authentication authentication = authenticationManager.authenticate(auth);
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token =  jwtService.generateToken(authentication);
		
		log.info("User Login Successfully with email : {}", authentication.getName());

		return new JwtResponse(token);
	}

}
