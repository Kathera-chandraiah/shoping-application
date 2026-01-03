package com.thacha.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thacha.entity.User;
import com.thacha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(String.format("Email %s is not found", username)));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		return user.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
	}

}
