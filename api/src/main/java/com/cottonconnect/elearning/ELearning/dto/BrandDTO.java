package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BrandDTO {
	private Long id;
	private String name;
	private String code;
	private String contactPersonName;
	private String contactNo;
	private String mobileNo;
	private String landLineNo;
	private String email;
	private List<Long> programsList;
	@NotNull(message = "User should not be null")
	@NotEmpty(message = "User should not be empty")
	private List<Long> userList;
	@NotNull(message = "Country should not be null")
	@NotEmpty(message = "Country should not be empty")
	private List<Long> countriesList;
	
	private String countries;
	private String programs;
	private String users;
}
