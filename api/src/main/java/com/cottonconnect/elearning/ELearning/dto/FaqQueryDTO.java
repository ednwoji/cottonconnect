package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import com.cottonconnect.elearning.ELearning.model.Device;
import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
