package com.thacha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thacha.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String Email);
	Optional<User> findByEmail(String email);

}
