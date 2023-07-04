package com.finallyz.oauth.service.dto;

import java.util.List;

import com.finallyz.oauth.service.domain.KnowledgeCenterImages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeCenterDTO {
	private Long id;
	private String name;
	private String identification;
	private String title;
	private String description;
	private String notes;
	private String photoUrl;
	private String subCategory;
	private String image;
	private String imgUrl;
	private List<KnowledgeCenterImages> images;
	private Long typez;
	private String redirectUrl;
	private String imagesByComma;
	private Long farmGroup;
	private Long program;
	private Long partner;

	private String externalLink;

	private List<Long> programs;
	private List<Long> localPartners;
	private List<Long> farmGroups;
	private List<Long> learners;
	
	private List<Long> countries;
	private List<Long> brands;
}
