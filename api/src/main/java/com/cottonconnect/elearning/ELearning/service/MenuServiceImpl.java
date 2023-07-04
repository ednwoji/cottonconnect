package com.cottonconnect.elearning.ELearning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;
import com.cottonconnect.elearning.ELearning.model.Menu;
import com.cottonconnect.elearning.ELearning.repo.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<MenuDTO> getAllParentMenu() {
		List<Menu> menuList = menuRepository.findByParentIdNullOrderBySeqAsc();
		List<MenuDTO> menuDTOList = menuList.stream().map(menu -> {
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setId(menu.getId());
			menuDTO.setDispName(menu.getDispName());
			menuDTO.setIcon(menu.getIcon());
			menuDTO.setName(menu.getName());
			menuDTO.setParentId(menu.getParentId());
			menuDTO.setSeq(menu.getSeq());
			menuDTO.setUrl(menu.getUrl());
			return menuDTO;
		}).collect(Collectors.toList());

		return menuDTOList;
	}

	@Override
	public List<MenuDTO> getMenuByParent(Long parentId) {
		List<Menu> menuList = menuRepository.findByParentIdOrderBySeqAsc(parentId);
		List<MenuDTO> menuDTOList = menuList.stream().map(menu -> {
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setId(menu.getId());
			menuDTO.setDispName(menu.getDispName());
			menuDTO.setIcon(menu.getIcon());
			menuDTO.setName(menu.getName());
			menuDTO.setParentId(menu.getParentId());
			menuDTO.setSeq(menu.getSeq());
			menuDTO.setUrl(menu.getUrl());
			return menuDTO;
		}).collect(Collectors.toList());

		return menuDTOList;
	}

	@Override
	public List<MenuDTO> getMenuByParents(List<Long> parentId) {
		List<Menu> menuList = menuRepository.findByParentIdInOrderByParentIdAscSeqAsc(parentId);
		List<MenuDTO> menuDTOList = menuList.stream().map(menu -> {
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setId(menu.getId());
			menuDTO.setDispName(menu.getDispName());
			menuDTO.setIcon(menu.getIcon());
			menuDTO.setName(menu.getName());
			menuDTO.setParentId(menu.getParentId());
			menuDTO.setSeq(menu.getSeq());
			menuDTO.setUrl(menu.getUrl());
			return menuDTO;
		}).collect(Collectors.toList());

		return menuDTOList;
	}

	@Override
	public List<Object[]> getMenuByUser(String user) {
		List<Object[]> menus = menuRepository.getMenuByUser(user);
		
		return menus;
	}

}
