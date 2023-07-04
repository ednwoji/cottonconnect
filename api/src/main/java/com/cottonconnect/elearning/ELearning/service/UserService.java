package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.LoginDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.UserDTO;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.model.User;

public interface UserService {
	public LoginDTO checkLogin(LoginDTO loginDTO);

	public UserDTO saveUser(UserDTO loginDto);

	public TableResponse getAllRecords(Integer draw, Integer start, Integer length,String search);
	
	List<UserDTO> getAllRecords();

	public User checkWebLogin(String userName, String password);

	public void registerUser(Register register);

	public UserDTO findById(Long valueOf);

	public void delete(Long id);
	
}
