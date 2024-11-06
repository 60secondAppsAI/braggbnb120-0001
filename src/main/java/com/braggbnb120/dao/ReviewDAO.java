package com.braggbnb120.dao;

import java.util.List;

import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.domain.Review;





public interface ReviewDAO extends GenericDAO<Review, Integer> {
  
	List<Review> findAll();
	






}


