package com.cottonconnect.elearning.ELearning.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.FarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.FarmerService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/service/famer")
public class FarmerController {
	@Autowired
	FarmerService farmerService;
	
	@RequestMapping(value = "/save")
	public ResponseEntity<FarmerDTO> save(HttpServletRequest request, @RequestBody FarmerDTO farmerDto) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		FarmerDTO resp = farmerService.saveFarmer(farmerDto, userId);
		ResponseEntity<FarmerDTO> farmerResp = new ResponseEntity<FarmerDTO>(resp, HttpStatus.CREATED);
		return farmerResp;
	}
	
	@RequestMapping(value = "/getAllFarmers")
	public ResponseEntity<TableResponse> getAllBrands(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {
		TableResponse farmerList = farmerService.getAllFarmers(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(farmerList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "by-id")
	public ResponseEntity<FarmerDTO> getFarmerById(@RequestParam(name = "id") Long id){
		FarmerDTO farmer = farmerService.findFarmerById(id);
		ResponseEntity<FarmerDTO> response = new ResponseEntity<FarmerDTO>(farmer, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "delete")
	public ResponseEntity<FarmerDTO> deleteById(@RequestParam(name = "id") Long id){
		farmerService.delete(id);
		ResponseEntity<FarmerDTO> response = new ResponseEntity<FarmerDTO>( HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "by-mobile")
	public ResponseEntity<FarmerDTO> getFarmerByMobile(@RequestParam(name = "mobile") String mobile){
		FarmerDTO farmer = farmerService.getFarmerByMobile(mobile);
		ResponseEntity<FarmerDTO> response = new ResponseEntity<FarmerDTO>(farmer, HttpStatus.OK);
		return response;
	}
	
}
