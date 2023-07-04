package com.finallyz.oauth.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class FarmerDTO {
	private Long id;
	
	private String name;

	private String mobileNumber;

	private String farmerCode;

	private Long typez;

	private String countryCode;

	private Long programmeId;

	private Long countryId;

	private Long farmGroupId;
	
	private String fieldExecutive;
	
	private String countryName;
	
	private String farmGroupName;
	
	private String programme;
	
	private String brandName;
	
	private Long learnerGroupId;
	
	private String imei;
	
	private String device;
	
	private Long brandId;
}
