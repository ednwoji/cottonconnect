package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
	private Long id;

	private String name;

	private List<Long> previlageId;

	private List<Long> menuIds;

	private List<String> previlageNames;

	private List<String> menuNames;

	public RoleDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
