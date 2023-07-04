package com.finallyz.oauth.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
	private Long id;
	private List<String> emails;
	private Long country;
	private Long brand;
	
	private List<Long> programs;
	private List<Long> localPartners;
	private List<Long> farmGroups;
	private List<Long> learners;
	
	private String redirectUrl;
}
