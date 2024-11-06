package com.braggbnb120.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer userId;
	
	private String userName;
	
	private String email;
	
	private String phoneNumber;
	
	private String profilePicture;
	
	private String about;
	
}
