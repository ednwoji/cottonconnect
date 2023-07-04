package com.finallyz.oauth.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqResponseDTO {
	private Long id;
	private Long queryId;

	private String query;

	public String url;
}
