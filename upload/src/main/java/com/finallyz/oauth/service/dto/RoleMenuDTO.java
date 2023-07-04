package com.finallyz.oauth.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMenuDTO {
	private Long id;

	private Long roleId;

	private List<Long> parentMenuId;

	private List<Long> menuId;
}
