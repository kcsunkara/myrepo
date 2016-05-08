package com.csc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Role;
import com.csc.jpa.repository.UserRepositoryJPA;

@Service("userServiceImpl")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepositoryJPA userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			com.csc.doc.User user = userRepository.findByUserName(username.toLowerCase());
			
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			return new User(
					user.getUserName(), 
					user.getPassword(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(user));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(com.csc.doc.User user) {
		List<Role> roles = user.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role :  roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}
	
}
