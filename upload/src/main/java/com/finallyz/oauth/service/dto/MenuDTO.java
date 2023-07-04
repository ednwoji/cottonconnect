package com.finallyz.oauth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
	private Long id;

	private String name;

	private String icon;

	private String dispName;

	private Long parentId;
	
	private String url;
	
	private Integer seq;
}
