package com.cottonconnect.elearning.ELearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TalukDTO {
	private Long id;

	private String code;

	private String name;

	private Long districtId;

	private String districtName;
	
	private Long selectedCountryId;
	
	private Long selectedState;

}
