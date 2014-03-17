package com.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

	public abstract UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException;

}