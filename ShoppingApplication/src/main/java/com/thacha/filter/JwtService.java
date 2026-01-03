package com.thacha.filter;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

	@Value("${app.jwt.secret}")
	private String secretKey;

	@Value("${app.jwt.expiration}")
	private long jwtExpirationMs;

	// generate Token
	public String generateToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime() + jwtExpirationMs);

		return Jwts.builder().subject(userDetails.getUsername()).issuedAt(currentDate).expiration(expiryDate)
				.signWith(generateSecretKey(secretKey)).compact();
	}

	private SecretKey generateSecretKey(String secretKey) {
		byte[] decode = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decode);
	}

	// Extract email from the token

	public String extractEmailFromToken(String token) {

		if (token == null) {
			return null;
		} else {
			return Jwts.parser().verifyWith(generateSecretKey(secretKey)).build().parseSignedClaims(token).getPayload()
					.getSubject();
		}

	}

	// validate token

	public boolean validateToken(String token, UserDetails userDetails) {
		String email = extractEmailFromToken(token);
		if (!email.equals(userDetails.getUsername())) {
			return false;
		}
		try {
			Jwts.parser().verifyWith(generateSecretKey(secretKey)).build().parseSignedClaims(token);
			return true;
		} catch (SignatureException ex) {

			log.error("Invalid Jwt Signature: {}", ex.getMessage());

		} catch (MalformedJwtException e) {

			log.error("Invalid Jwt Token : {}", e.getMessage());

		} catch (ExpiredJwtException e) {

			log.error("Jwt Token is expired : {}", e.getMessage());

		} catch (UnsupportedJwtException e) {

			log.error("Jwt token is unsupported: {}", e.getMessage());
		}

		return false;

	}

}
