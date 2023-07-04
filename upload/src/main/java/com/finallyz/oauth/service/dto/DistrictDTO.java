package com.finallyz.oauth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {
	private Long id;

	private String code;

	private String name;

	private Long stateId;

	private String stateName;
	
	private Long selectedCountryId;
}
