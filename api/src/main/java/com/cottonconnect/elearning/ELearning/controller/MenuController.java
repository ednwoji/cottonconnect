package com.cottonconnect.elearning.ELearning.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;
import com.cottonconnect.elearning.ELearning.model.Entitlements;
import com.cottonconnect.elearning.ELearning.model.Menu;
import com.cottonconnect.elearning.ELearning.repo.EntitlementRepository;
import com.cottonconnect.elearning.ELearning.repo.MenuRepository;
import com.cottonconnect.elearning.ELearning.service.MenuService;

@RestController
@RequestMapping(value = "/user/menu")
public class MenuController {
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private EntitlementRepository entRepo;
	
	@Autowired
	MenuService menuService;

	@RequestMapping("/save")
	public ResponseEntity<List<Menu>> saveMenu(HttpServletRequest request, @RequestBody List<Menu> menuList) {
		menuRepository.saveAll(menuList);

		List<Entitlements> entList = new ArrayList<Entitlements>();
		menuList.stream().forEach(menu -> {
			menu.getEntitlements().forEach(ent->{
				ent.setMenu(menu);
				entList.add(ent);
			});
		});

		entRepo.saveAll(entList);
		return null;
	}
	
	@RequestMapping("/parent-menu")
	public ResponseEntity<List<MenuDTO>> getParentMenu(){
		List<MenuDTO> menuList =  menuService.getAllParentMenu();
		return new ResponseEntity<List<MenuDTO>>(menuList, HttpStatus.OK);
		
	}
	
	@RequestMapping("/by-parent")
	public ResponseEntity<List<MenuDTO>> getMenuByParent(@RequestParam Long parentId){
		List<MenuDTO> menuList =  menuService.getMenuByParent(parentId);
		return new ResponseEntity<List<MenuDTO>>(menuList, HttpStatus.OK);
		
	}
	
	@RequestMapping("/by-parents")
	public ResponseEntity<List<MenuDTO>> getMenuByParents(@RequestParam List<Long> parentId){
		List<MenuDTO> menuList =  menuService.getMenuByParents(parentId);
		return new ResponseEntity<List<MenuDTO>>(menuList, HttpStatus.OK);
		
	}
	
	
	@RequestMapping("/getMenuByUser")
	public ResponseEntity<List<Object[]>> getMenuByUser(@RequestParam(value = "user") String user){
		List<Object[]> menus = menuService.getMenuByUser(user);
		return new ResponseEntity<>(menus, HttpStatus.OK);
	}
}
