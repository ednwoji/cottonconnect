package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;
import com.cottonconnect.elearning.ELearning.dto.MessageDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleEntitlementDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleMenuDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.service.RoleService;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

	@Autowired
	RoleService roleService;

	@RequestMapping("/save")
	public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO roleDTO) {
		RoleDTO responseDTO = roleService.addRole(roleDTO);
		return (responseDTO == null) ? new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<RoleDTO>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllRoles")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse countryList = roleService.getTableRoles(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/getAllRoleMenus")
	public ResponseEntity<TableResponse> getAllRoleMenus(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse countryList = roleService.getTableRoleMenu(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping("/getRoles")
	public ResponseEntity<List<RoleDTO>> getRoles() {
		ResponseEntity<List<RoleDTO>> carouselResponse = new ResponseEntity<List<RoleDTO>>(roleService.getAllRoles(),
				HttpStatus.OK);
		return carouselResponse;
	}
	
	@RequestMapping("/by-id")
	public ResponseEntity<RoleDTO> getRoleById(@RequestParam Long id) {
		ResponseEntity<RoleDTO> roleResponse = new ResponseEntity<RoleDTO>(roleService.getRoleById(id), HttpStatus.OK);
		return roleResponse;
	}

	@RequestMapping("/getMenus")
	public ResponseEntity<List<MenuDTO>> getMenus() {
		ResponseEntity<List<MenuDTO>> roleMenuResponse = new ResponseEntity<List<MenuDTO>>(roleService.getAllMenus(),
				HttpStatus.OK);
		return roleMenuResponse;
	}
	
	@RequestMapping("/menu/by-role")
	public ResponseEntity<List<MenuDTO>> findByRole(@RequestParam(value = "role") Long role) {
		ResponseEntity<List<MenuDTO>> roleMenuResponse = new ResponseEntity<List<MenuDTO>>(roleService.findByRole(role),
				HttpStatus.OK);
		return roleMenuResponse;
	}

	
	@RequestMapping("/save-role-menu")
	public ResponseEntity<RoleMenuDTO> saveRoleMenu(@RequestBody RoleMenuDTO menuDTO){
		RoleMenuDTO response = roleService.saveRoleMenu(menuDTO);
		ResponseEntity<RoleMenuDTO> roles = new ResponseEntity<RoleMenuDTO>(response, HttpStatus.OK);
		return roles;
	}
	
	@RequestMapping("/save-role-entitlement")
	public ResponseEntity<MessageDTO> saveRoleEnt(@RequestBody RoleEntitlementDTO roleEntitlement){
		try {
			MessageDTO messageDTO = roleService.saveRoleEntitlement(roleEntitlement);
			return new ResponseEntity<MessageDTO>(messageDTO, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/entitlements/by-menu")
	public ResponseEntity<Map<Object, List<Object[]>>> getEntitlementsByMenu(@RequestParam(value = "menus") List<Long> menus){
		Map<Object, List<Object[]>> entitlementsList = roleService.getEntitlementsByMenu(menus);
		return new ResponseEntity<Map<Object,List<Object[]>>>(entitlementsList, HttpStatus.OK);
	}
	
	@RequestMapping("/entitlements/by-role")
	public ResponseEntity<RoleEntitlementDTO> getEntitlementsByRole(@RequestParam(value = "role") Long role){
		RoleEntitlementDTO entitlements = roleService.getEntitlementsByRole(role);
		return new ResponseEntity<RoleEntitlementDTO>(entitlements, HttpStatus.OK);
	}
}
