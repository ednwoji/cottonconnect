package com.finallyz.oauth.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class NotificationDTO {
	private Long id;
	private String description;
	private String msg;
	private Long farmGroup;
	private Long country;
	private Long brand;
	private Long program;
	private Long partner;
	private String redirectUrl;
	private String createdDate;
	private Long learnerGroup;
	
	private List<Long> programs;
	private List<Long> localPartners;
	private List<Long> farmGroups;
	private List<Long> learners;
}
