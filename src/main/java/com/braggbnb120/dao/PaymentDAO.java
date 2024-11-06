package com.braggbnb120.dao;

import java.util.List;

import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


