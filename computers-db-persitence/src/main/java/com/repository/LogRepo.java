package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.om.Log;


public interface LogRepo extends JpaRepository<Log , Long> {
	
	@SuppressWarnings("unchecked")
	public Log save(Log log);

}
