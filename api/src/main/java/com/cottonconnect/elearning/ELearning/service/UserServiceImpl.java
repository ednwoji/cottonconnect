package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.LoginDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.UserDTO;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.model.Role;
import com.cottonconnect.elearning.ELearning.model.User;
import com.cottonconnect.elearning.ELearning.repo.RegisterRepository;
import com.cottonconnect.elearning.ELearning.repo.RoleRepository;
import com.cottonconnect.elearning.ELearning.repo.UserPaginatedRepository;
import com.cottonconnect.elearning.ELearning.repo.UserRepository;
import com.utility.Mapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserPaginatedRepository userPaginatedRepo;
	@Autowired
	RegisterRepository registerRepository;

	@Override
	public LoginDTO checkLogin(LoginDTO loginDTO) {
		Optional<User> userOpt = userRepository.findByMobileNo(loginDTO.getMobileNumber());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			loginDTO.setUserName(user.getUserId());
			return loginDTO;
		}
		return null;
	}

	@Transactional
	@Override
	public UserDTO saveUser(UserDTO userDto) {
		User user = Mapper.map(userDto, User.class);
		Mapper.setAuditable(user, "");
		Role role = roleRepository.getOne(userDto.getRoleId());
		user.setRole(role);
		userRepository.save(user);
		return userDto;
	}

	@Override
	public TableResponse getAllRecords(Integer draw, Integer pageNo, Integer pageSize, String search) {
		TableResponse response = null;
		List<List<Object>> kcDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> kcPaged = userPaginatedRepo.findAllWithPage(search.toLowerCase(), paging);
		if (kcPaged.hasContent()) {
			List<User> kcList = kcPaged.getContent();
			kcDtoList = kcList.stream().map(kc -> {
				List<Object> kcObjList = new ArrayList<>();
				kcObjList.add(kc.getUserId());
				kcObjList.add(kc.getName());
				kcObjList.add(kc.getEmailId());
				kcObjList.add(kc.getMobileNo());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ kc.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + kc.getId()
						+ "')></button>");
				kcObjList.add(sb.toString());
				return kcObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, kcPaged.getTotalElements(), kcPaged.getTotalElements(), kcDtoList);
		} else {
			response = new TableResponse(draw, kcPaged.getTotalElements(), kcPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;
	}

	@Override
	public User checkWebLogin(String userName, String password) {
		Optional<User> userOpt = userRepository.checkLogin(userName, password);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
	}

	@Override
	public List<UserDTO> getAllRecords() {
		List<User> userList = userRepository.findAll();
		List<UserDTO> userDtoLit = userList.stream().map(user -> {
			UserDTO userDto = Mapper.map(user, UserDTO.class);
			return userDto;
		}).collect(Collectors.toList());
		return userDtoLit;
	}

	@Override
	public void registerUser(Register register) {
		registerRepository.save(register);
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			UserDTO userDto = new UserDTO();
			userDto.setId(user.getId());
			userDto.setAccessLevel(user.getAccessLevel());
			userDto.setDeviceId(user.getDeviceId());
			userDto.setName(user.getName());
			userDto.setEmailId(user.getEmailId());
			userDto.setIcsId(user.getIcsId());
			userDto.setLpnId(user.getLpnId());
			userDto.setMobileNo(user.getMobileNo());
			userDto.setProgrammeId(user.getProgrammeId());
			userDto.setRoleId(user.getRole().getId());
			userDto.setUserId(user.getUserId());
			userDto.setAccessLevel(user.getAccessLevel());
			userDto.setPassword(user.getPassword());
			return userDto;
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setDeleted(true);
			userRepository.save(user);
		}
	}


}
