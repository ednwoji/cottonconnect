package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VillageDTO {
	private Long id;

	private String code;

	private String name;

	private Long talukId;

	private String talukName;

	private Long selectedCountryId;

	private Long selectedState;

	private Long selectedDistrict;
}
