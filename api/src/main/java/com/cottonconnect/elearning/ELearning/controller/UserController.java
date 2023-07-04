package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cottonconnect.elearning.ELearning.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.LoginDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.UserDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.model.User;
import com.cottonconnect.elearning.ELearning.service.FarmerService;
import com.cottonconnect.elearning.ELearning.service.UserService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/service/user")
@Slf4j
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	FarmerService farmerService;

	@RequestMapping(value = "/app/login")
	public ResponseEntity<LoginDTO> checkLogin(@RequestBody LoginDTO loginDTO) {
		LoginDTO responseDTO = farmerService.checkLogin(loginDTO);
		if (responseDTO == null) {
			return new ResponseEntity<LoginDTO>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<LoginDTO>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/web/login")
	public ResponseEntity<User> checkPotalLogin(@RequestBody LoginDTO loginDTO) {
		User user = userService.checkWebLogin(loginDTO.getUserName(), loginDTO.getPassword());
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveUser")
	public ResponseEntity<Response> save(@RequestBody UserDTO userDTO, Response response) {
		log.info("userDTO");
		response = userService.saveUser(userDTO);
		return response.getCode().equals("00") ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

//		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/register")
	public ResponseEntity<Register> save(@RequestBody Register register) {
		userService.registerUser(register);
		return new ResponseEntity<Register>(register, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllRecords")
	public ResponseEntity<TableResponse> getAllRecords(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse stateList = userService.getAllRecords(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getUsers")
	public ResponseEntity<List<UserDTO>> getRecords(HttpServletRequest request) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		return new ResponseEntity<List<UserDTO>>(userService.getAllRecords(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/by-id")
	public ResponseEntity<UserDTO> getUserById(@RequestParam("id") String id) {
		UserDTO userDTO = userService.findById(Long.valueOf(id));
		ResponseEntity<UserDTO> response = new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/delete")
	public ResponseEntity<UserDTO> delete(@RequestParam("id") String id) {
		userService.delete(Long.valueOf(id));
		return new ResponseEntity<UserDTO>(HttpStatus.OK);
	}
}
