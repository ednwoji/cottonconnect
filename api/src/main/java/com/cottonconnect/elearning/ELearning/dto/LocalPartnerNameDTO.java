package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalPartnerNameDTO {
	private Long id;
	private String name;
	private String address;
	private Long countryId;
	private List<Long> programId;
	private Long active;
	private String countries;
	private String brands;
	private String programs;
	private String status;
	private Long brandId;
	private Long statusz;
	private String redirectUrl;
}
