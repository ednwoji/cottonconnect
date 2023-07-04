package com.cottonconnect.elearning.ELearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
	private Long id;

	private String code;

	private String name;

	private Long countryId;

	private String countryName;
}
