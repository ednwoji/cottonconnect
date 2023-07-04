package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
	private Long id;
	private List<Long> programs;
	private List<Long> localPartners;
	private List<Long> farmGroups;
	private List<Long> learners;

	private List<Long> countries;
	private List<Long> brands;

	private String programName;
	private String partnerName;
	private String farmGroupName;
	private String learnerName;
	private String countryName;
	private String brandName;

	public String name;
	public String mobileNumber;
	public String imei;
	public String lat;
	public String lon;
	private String manufacturer;
	private boolean isApproved;
	private String organisation;
}
