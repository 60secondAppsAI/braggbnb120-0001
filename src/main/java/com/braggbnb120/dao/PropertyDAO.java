package com.braggbnb120.dao;

import java.util.List;

import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.domain.Property;





public interface PropertyDAO extends GenericDAO<Property, Integer> {
  
	List<Property> findAll();
	






}


