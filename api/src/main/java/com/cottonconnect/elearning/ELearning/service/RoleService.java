package com.cottonconnect.elearning.ELearning.service;

import java.util.List;
import java.util.Map;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;
import com.cottonconnect.elearning.ELearning.dto.MessageDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleEntitlementDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleMenuDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface RoleService {
	RoleDTO addRole(RoleDTO roleDto);

	List<RoleDTO> getAllRoles();

	List<Object[]> getMenuByUser(String userName);

	Map<Object, List<Object[]>> getEntitlementsByMenu(List<Long> menus);
	
	List<MenuDTO> getAllMenus();
	
	TableResponse getTableRoles(Integer draw,Integer pageNo, Integer pageSize);
	
	RoleDTO getRoleById(Long id);

	RoleMenuDTO saveRoleMenu(RoleMenuDTO menuDTO);

	TableResponse getTableRoleMenu(Integer draw, Integer start, Integer length);

	List<MenuDTO> findByRole(Long role);

	MessageDTO saveRoleEntitlement(RoleEntitlementDTO roleEntitlement);

	RoleEntitlementDTO getEntitlementsByRole(Long role);
}
