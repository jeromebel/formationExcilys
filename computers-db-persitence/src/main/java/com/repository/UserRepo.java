package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.om.User;

public interface UserRepo extends JpaRepository<User , Long> {
	
	public User findByUserName(String userName);

}
