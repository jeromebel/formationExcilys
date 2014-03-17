package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.om.Computer;

@Repository
public interface ComputerRepo extends JpaRepository<Computer, Long>{

	public Computer findById(Long id);
	
	public void delete(Computer c);
		
	@SuppressWarnings("unchecked")
	public Computer save(Computer c);
		
	public Page<Computer> findAll(Pageable pageRequest);
	
	public Page<Computer> findByNameLike(Pageable pageRequest , String name);
	
	
}
