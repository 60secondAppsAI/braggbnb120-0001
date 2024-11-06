package com.braggbnb120.dao;

import java.util.List;

import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.domain.User;

import java.util.Optional;




public interface UserDAO extends GenericDAO<User, Integer> {
  
	List<User> findAll();
	






}


