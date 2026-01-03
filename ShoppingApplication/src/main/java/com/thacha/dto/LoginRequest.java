package com.thacha.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	@Email(message="Invalid email address")
	@NotBlank(message="email must not be blank")
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min=8 , max=16 , message="Password must be 8-16 characters")
	private String password;
}
