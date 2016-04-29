package com.csc.service;

import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class AuthenticationService {
	public boolean authenticate(String authCredentials) {
		if (null == authCredentials) {
			return false;
		}
		boolean authenticationStatus = false;
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		try {
			final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
			String usernameAndPassword = null;

			byte[] decodedBytes = Base64Utils.decodeFromString(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");

			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			// we have fixed the userid and password as admin
			// call some UserService/LDAP here
			authenticationStatus = "admin".equals(username)	&& "admin".equals(password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return authenticationStatus;
	}
}
