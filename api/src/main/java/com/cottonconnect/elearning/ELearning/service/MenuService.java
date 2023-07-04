package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;

public interface MenuService {
	public List<MenuDTO> getAllParentMenu();
	
	public List<MenuDTO> getMenuByParent(Long parentId);

	public List<MenuDTO> getMenuByParents(List<Long> parentId);

	public List<Object[]> getMenuByUser(String user);
}
