package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@WebService
public interface UserService {

	@WebMethod
	public abstract UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException;

}