package com.cottonconnect.elearning.ELearning.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.RegisterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.Device;
import com.cottonconnect.elearning.ELearning.service.DeviceService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {
	@Autowired
	DeviceService deviceService;

	@RequestMapping(value = "/save")
	public ResponseEntity<Device> saveDevice(HttpServletRequest request, @RequestBody Device device)
			throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");

		device = deviceService.saveDevice(device, userId);
		return new ResponseEntity<Device>(device, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllDevices")
	public ResponseEntity<TableResponse> getAllDevices(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse countryList = deviceService.getTableDevices(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getAllUnRegister")
	public ResponseEntity<TableResponse> getAllUnRegister(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse countryList = deviceService.getAllUnRegister(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/register/by-id")
	public ResponseEntity<RegisterDTO> getRegisterById(@RequestParam(name = "id") Long id){
		RegisterDTO register = deviceService.getRegisterById(id);
		ResponseEntity<RegisterDTO> response = new ResponseEntity<RegisterDTO>(register, HttpStatus.OK);
		return response;
	}

	@RequestMapping("/approve")
	public ResponseEntity<?> approve(@RequestBody RegisterDTO masterDTO) {
		deviceService.approve(masterDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register/delete")
	public ResponseEntity delete(@RequestParam(name = "id") Long id){
		RegisterDTO register = deviceService.deleteById(id);
		ResponseEntity<RegisterDTO> response = new ResponseEntity<RegisterDTO>(register, HttpStatus.OK);
		return response;
	}

}
