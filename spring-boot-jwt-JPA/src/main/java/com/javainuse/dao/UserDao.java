package com.javainuse.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javainuse.model.Login;

@Repository
public interface UserDao extends CrudRepository<Login, Integer> {
	
	Login findByUsername(String username);
	
}