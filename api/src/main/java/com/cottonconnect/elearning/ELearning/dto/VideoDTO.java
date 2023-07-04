package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
	private Long id;

	private String name;

	private String description;

	private boolean isRenderingCompleted;

	private String url;

	private String link;

	private boolean isRendered;

	private String redirectUrl;

	private String status;
	private List<Long> countries;
	private List<Long> brands;
	private Long program;
	private Long partner;

	private List<Long> programs;
	private List<Long> localPartners;
	private List<Long> farmGroups;
	private List<Long> learners;

	private String sourceUrl;
}
