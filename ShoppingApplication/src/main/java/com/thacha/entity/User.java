package com.thacha.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name="confirmPassword",nullable = false)
	private String confirmPassword;
	
//	@ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
//	@Enumerated(EnumType.STRING)
//	@CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
//	@Column(name="role")
//	private Set<Role> roles = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
    @Column(name = "role" ,nullable = false)
    private Role role;

}
