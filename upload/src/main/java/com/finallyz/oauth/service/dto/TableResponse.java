package com.finallyz.oauth.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TableResponse {
	private Integer draw;
	private Object recordsTotal;
	private Object recordsFiltered;
	private List<?> data;
}
