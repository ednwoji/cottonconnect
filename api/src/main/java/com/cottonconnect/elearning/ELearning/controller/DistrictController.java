package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.DistrictDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.service.DistrictService;

@RestController
@RequestMapping(value = "/location/district")
public class DistrictController {
	@Autowired
	DistrictService districtService;

	@RequestMapping(value = "/saveDistrict")
	public ResponseEntity<DistrictDTO> saveDistrict(@RequestBody DistrictDTO districtDto) {
		DistrictDTO resp = districtService.saveDistrict(districtDto);
		ResponseEntity<DistrictDTO> districtResp = new ResponseEntity<DistrictDTO>(resp, HttpStatus.CREATED);
		return districtResp;

	}

	@RequestMapping(value = "/getAllDistricts")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse stateList = districtService.getAllDistricts(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getDistrictsByState")
	public ResponseEntity<List<DistrictDTO>> getCountries(@RequestParam("stateId") Long stateId) {
		List<DistrictDTO> districtList = districtService.getDistricts(stateId);
		ResponseEntity<List<DistrictDTO>> response = new ResponseEntity<List<DistrictDTO>>(districtList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/by-id/{id}")
	public ResponseEntity<DistrictDTO> byId(@PathVariable("id") Long id) {
		DistrictDTO resp = districtService.getById(id);
		ResponseEntity<DistrictDTO> districtResp = new ResponseEntity<DistrictDTO>(resp, HttpStatus.CREATED);
		return districtResp;
	}
	
	@RequestMapping(value = "/delete")
	public ResponseEntity<DistrictDTO> deleteById(@RequestParam(name = "id") Long id){
		districtService.delete(id);
		ResponseEntity<DistrictDTO> response = new ResponseEntity<DistrictDTO>( HttpStatus.OK);
		return response;
	}
}
