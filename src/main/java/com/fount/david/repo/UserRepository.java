package com.fount.david.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fount.david.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
