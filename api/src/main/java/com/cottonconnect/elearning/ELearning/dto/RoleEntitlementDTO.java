package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleEntitlementDTO {
	private Long roleId;
	private List<String> ents;
}
