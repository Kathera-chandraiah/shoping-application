package com.thacha.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thacha.dto.SignupRequest;
import com.thacha.dto.SignupResponse;
import com.thacha.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "id" , ignore = true)
	@Mapping(source="roles",target="roles")
	User mapSignUpRequestToUser(SignupRequest signUpRequest);
	
	@Mapping(target = "password",ignore = true)
	SignupResponse mapUserToSignupResponse(User user);
}
