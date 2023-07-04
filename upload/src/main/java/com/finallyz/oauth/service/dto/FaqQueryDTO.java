package com.finallyz.oauth.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.finallyz.oauth.service.domain.Device;
import com.finallyz.oauth.service.domain.FaqQueryResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class FaqQueryDTO {
	private Long id;
	private String name;
	private List<Document> documents;
	private String imei;
	private FarmerDTO farmer;
	private Device devie;
	private String createdDate;
	private String deviceName;
	private List<FaqQueryResponse> faqResponse;
}
