package com.finallyz.oauth.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearnerGroupDTO {
	private Long id;
	private String name;
	private String latitude;
	private String longitude;
	private Long noOfFarmers;
	private Long acreage;
	private Long estYield;
	private Long farmGroupId;
	private Long brandId;
	private Long programId;
	private Long partnerId;
	private Long countryId;
}
