package com.braggbnb120.dao;

import java.util.List;

import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.domain.Message;





public interface MessageDAO extends GenericDAO<Message, Integer> {
  
	List<Message> findAll();
	






}


