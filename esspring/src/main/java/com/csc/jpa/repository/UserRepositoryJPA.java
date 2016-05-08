package com.csc.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.User;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, String> {
	User findByUserName(String username);
}
