package com.braggbnb120.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BookingDTO {

	private Integer bookingId;

	private Date startDate;

	private Date endDate;

	private String bookingStatus;

	private double totalPrice;






}
