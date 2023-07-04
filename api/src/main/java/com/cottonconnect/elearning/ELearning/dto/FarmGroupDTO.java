package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmGroupDTO {
	private Long id;

	private Long typez;

	private String name;

	private String latitude;

	private String longitude;

	private Long noOfFarmers;

	private Long acreage;

	private Long estYield;

	private Long countryId;

	private Long partnerId;
	
	private Long brandId;
	
	private Long programId;
	
}
