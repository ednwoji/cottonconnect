package com.cottonconnect.elearning.ELearning.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDTO {
	private Long id;

	private String code;

	@NotNull(message = "Country name should not be null")
	@NotEmpty(message = "Country name should not be empty")
	private String name;
}
