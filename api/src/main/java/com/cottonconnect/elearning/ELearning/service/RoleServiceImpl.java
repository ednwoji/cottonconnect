package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.MenuDTO;
import com.cottonconnect.elearning.ELearning.dto.MessageDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleEntitlementDTO;
import com.cottonconnect.elearning.ELearning.dto.RoleMenuDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Entitlements;
import com.cottonconnect.elearning.ELearning.model.Menu;
import com.cottonconnect.elearning.ELearning.model.Role;
import com.cottonconnect.elearning.ELearning.repo.EntitlementRepository;
import com.cottonconnect.elearning.ELearning.repo.MenuRepository;
import com.cottonconnect.elearning.ELearning.repo.RoleRepository;
import com.cottonconnect.elearning.ELearning.repo.page.RolePagedRepository;
import com.utility.Mapper;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository roleMenuRepo;
	@Autowired
	private RolePagedRepository rolePagedRepo;
	@Autowired
	private EntitlementRepository entitlementRepository;

	@Override
	public RoleDTO addRole(RoleDTO roleDto) {
		Role role = Mapper.map(roleDto, Role.class);
		Mapper.setAuditable(role, "");
		Role checkName = roleRepository.findByName(role.getName());
		log.info("Record is "+checkName);
		if(checkName==null) {
			log.info("Role does not have a duplicate");
			roleRepository.save(role);
			return roleDto;
		}
		else {
			log.info("Role already exists");
			return null;
		}

	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<RoleDTO> roleDtoList = roleRepository.findAll().stream().map(role -> {
			RoleDTO roleDto = new RoleDTO(role.getId(), role.getName());
			return roleDto;
		}).collect(Collectors.toList());

		return roleDtoList;
	}

	@Override
	public List<Object[]> getMenuByUser(String userName) {

		return roleMenuRepo.getMenuByUser(userName);
	}

	@Override
	public Map<Object, List<Object[]>> getEntitlementsByMenu(List<Long> menus) {
		List<Long> menuIdList = roleMenuRepo.findByParentIdInOrderByParentIdAscSeqAsc(menus).stream().map(menu -> menu.getId())
				.collect(Collectors.toList());

		List<Object[]> entitlements = roleMenuRepo.getEntitlementsByMenu(menuIdList);

		Map<Object, List<Object[]>> map = entitlements.stream()
				.collect(Collectors.groupingBy(obj -> obj[1], Collectors.toList()));
		return map;
	}

	@Override
	public List<MenuDTO> getAllMenus() {
		List<MenuDTO> menuDtoList = roleMenuRepo.findAll().stream()
				.filter(menu -> menu.getParentId() != null && menu.getParentId() > 0).map(menu -> {
					MenuDTO roleDto = new MenuDTO(menu.getId(), menu.getName(), menu.getIcon(), menu.getDispName(),
							menu.getParentId(), menu.getUrl(), menu.getSeq());
					return roleDto;
				}).collect(Collectors.toList());

		return menuDtoList;
	}

	@Override
	public TableResponse getTableRoles(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> roleDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Role> rolePaged = rolePagedRepo.findAll(paging);
		if (rolePaged.hasContent()) {
			List<Role> roleList = rolePaged.getContent();
			roleDtoList = roleList.stream().map(role -> {
				List<Object> countryObjList = new ArrayList<>();
				countryObjList.add(role.getName());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ role.getId() + "')></button>");
				/*
				 * sb.
				 * append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
				 * + role.getId() + "')></button>");
				 */
				countryObjList.add(sb.toString());
				return countryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, rolePaged.getTotalElements(), rolePaged.getTotalElements(), roleDtoList);
		} else {
			response = new TableResponse(draw, rolePaged.getTotalElements(), rolePaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public RoleDTO getRoleById(Long id) {
		Optional<Role> roleOpt = roleRepository.findById(id);
		RoleDTO roleDTO = new RoleDTO();
		if (roleOpt.isPresent()) {
			Role role = roleOpt.get();
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
		}
		return roleDTO;
	}

	@Override
	public RoleMenuDTO saveRoleMenu(RoleMenuDTO menuDTO) {
		Role role = roleRepository.findById(menuDTO.getRoleId()).get();
		List<Long> menus = new ArrayList<>();
		menus.addAll(menuDTO.getParentMenuId());
		menus.addAll(menuDTO.getMenuId());

		List<Menu> menuList = roleMenuRepo.findByIdIn(menus);
		role.setMenus(menuList);
		roleRepository.save(role);
		return menuDTO;
	}

	@Override
	public MessageDTO saveRoleEntitlement(RoleEntitlementDTO roleEntitlement) {
		Role role = roleRepository.findById(roleEntitlement.getRoleId()).get();
		List<Entitlements> ents = entitlementRepository.findByNameIn(roleEntitlement.getEnts());
		role.setEntitlements(ents);
		roleRepository.save(role);
		MessageDTO message = new MessageDTO(200, "Success");
		return message;
	}

	@Override
	public TableResponse getTableRoleMenu(Integer draw, Integer pageNo, Integer pageSize) {

		TableResponse response = null;
		List<List<Object>> roleDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Role> rolePaged = rolePagedRepo.findAll(paging);
		if (rolePaged.hasContent()) {
			List<Role> roleList = rolePaged.getContent();
			roleDtoList = roleList.stream().map(role -> {
				List<Object> countryObjList = new ArrayList<>();
				countryObjList.add("<a href='#' onclick='detail(" + role.getId() + ")'>" + role.getName() + "</a>");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ role.getId() + "')></button>");
				/*
				 * sb.
				 * append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
				 * + role.getId() + "')></button>");
				 */
				countryObjList.add(sb.toString());
				return countryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, rolePaged.getTotalElements(), rolePaged.getTotalElements(), roleDtoList);
		} else {
			response = new TableResponse(draw, rolePaged.getTotalElements(), rolePaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<MenuDTO> findByRole(Long roleId) {
		Optional<Role> roleOpt = roleRepository.findById(roleId);
		List<MenuDTO> menuDtoList = new ArrayList<>();
		if (roleOpt.isPresent()) {
			menuDtoList = roleOpt.get().getMenus().stream().filter(menu -> menu.getParentId() == null).map(menu -> {
				MenuDTO roleDto = new MenuDTO(menu.getId(), menu.getName(), menu.getIcon(), menu.getDispName(),
						menu.getParentId(), menu.getUrl(), menu.getSeq());
				return roleDto;
			}).collect(Collectors.toList());
		}

		return menuDtoList;
	}

	@Override
	public RoleEntitlementDTO getEntitlementsByRole(Long roleId) {
		Optional<Role> roleOpt = roleRepository.findById(roleId);
		RoleEntitlementDTO roleEnt = new RoleEntitlementDTO();
		if (roleOpt.isPresent()) {
			Role role = roleOpt.get();
			if(role.getEntitlements()!=null) {
				List<String> ents = role.getEntitlements().stream().map(ent->ent.getName()).collect(Collectors.toList());
				roleEnt.setRoleId(roleId);
				roleEnt.setEnts(ents);
			}
		}
		return roleEnt;
	}

}
