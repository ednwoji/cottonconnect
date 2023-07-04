package com.cottonconnect.elearning.ELearning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Document {
	private String docType;
	private String fileName;
	private String content;
	private String url;
}
