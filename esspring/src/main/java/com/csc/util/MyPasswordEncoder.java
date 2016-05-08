package com.csc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MyPasswordEncoder {

  public static void main(String[] args) {

	int i = 0;
	while (i < 5) {
		String adminPassword = "admin123";
		String userPassword = "user123";
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String adminHashedPassword = passwordEncoder.encode(adminPassword);
		String userHashedPassword = passwordEncoder.encode(userPassword);
		
		System.out.println(adminHashedPassword +"  "+ userHashedPassword);
		i++;
	}

  }
}