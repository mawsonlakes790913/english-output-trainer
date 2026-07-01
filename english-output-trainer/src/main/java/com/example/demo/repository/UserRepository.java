package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByUserId(String userId);
	
	boolean existsByUserId(String userId);

	void deleteByUserId(String userId);

}