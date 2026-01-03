package com.thacha.dto;

import java.util.Set;

import com.thacha.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	
	
	@Email(message="Invalid email address")
	@NotBlank(message="email must not be blank")
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min=8 , max=16 , message="Password must be 8-16 characters")
	private String password;
	
	@NotBlank(message="Confirm password is required")
	@Size(min=8 , max=16 , message="Password must be 8-16 characters")
	private String confirmPassword;
	
	private Set<Role> roles;
}
