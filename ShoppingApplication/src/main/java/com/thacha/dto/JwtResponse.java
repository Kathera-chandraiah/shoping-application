package com.thacha.dto;

import lombok.Getter;

@Getter
public class JwtResponse {
	
	private String token;
	private String tokenType = "Bearer";
	
	public JwtResponse(String token) {
		this.token = token;
	}
}
