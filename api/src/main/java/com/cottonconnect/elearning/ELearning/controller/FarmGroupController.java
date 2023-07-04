package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.BrandDTO;
import com.cottonconnect.elearning.ELearning.dto.FarmGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.FarmGroupService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/master")
@Slf4j
public class FarmGroupController {
	@Autowired
	FarmGroupService farmGroupService;

	@RequestMapping(value = "/farm-group/save")
	public ResponseEntity<FarmGroupDTO> saveFarmGroup(HttpServletRequest request,
			@RequestBody FarmGroupDTO farmGroupDTO) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		FarmGroupDTO resp = farmGroupService.saveFarmGroup(farmGroupDTO, userId);
		ResponseEntity<FarmGroupDTO> farmGroupResponse = new ResponseEntity<FarmGroupDTO>(resp, HttpStatus.CREATED);
		return farmGroupResponse;
	}

	@RequestMapping(value = "/farm-group/getFarmGroup")
	public ResponseEntity<List<FarmGroupDTO>> getFarmGroup() {
		List<FarmGroupDTO> farmGroupList = farmGroupService.getFarmGroups();
		ResponseEntity<List<FarmGroupDTO>> farmGroupResponse = new ResponseEntity<List<FarmGroupDTO>>(farmGroupList,
				HttpStatus.OK);
		return farmGroupResponse;
	}

	@RequestMapping(value = "/farm-group/partner")
	public ResponseEntity<List<FarmGroupDTO>> getFarmerGroupByPartner(HttpServletRequest request,
			@RequestParam("partner") Long partnerId) {
		List<FarmGroupDTO> farmGroupList = farmGroupService.getFarmGroupByPartner(partnerId);
		ResponseEntity<List<FarmGroupDTO>> response = new ResponseEntity<List<FarmGroupDTO>>(farmGroupList,
				HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/farm-group/partners")
	public ResponseEntity<List<FarmGroupDTO>> getFarmerGroupByPartner(HttpServletRequest request,
			@RequestParam("partners") List<Long> partners ) {

		log.info("Partners are "+partners);
		List<FarmGroupDTO> farmGroupList = farmGroupService.getFarmGroupByPartners(partners);
		ResponseEntity<List<FarmGroupDTO>> response = new ResponseEntity<List<FarmGroupDTO>>(farmGroupList,
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/farm-group/getAllFarmGroup")
	public ResponseEntity<TableResponse> getAllFarmGroup(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse countryList = farmGroupService.getAllFarmGroups(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/farm-group/by-id/{id}")
	public ResponseEntity<FarmGroupDTO> getFarmGroupById(@PathVariable("id") Long id) {
		FarmGroupDTO resp = farmGroupService.getById(id);
		ResponseEntity<FarmGroupDTO> partnerResp = new ResponseEntity<FarmGroupDTO>(resp, HttpStatus.OK);
		return partnerResp;
	}

	@RequestMapping(value = "/farm-group/by-id")
	public ResponseEntity<List<FarmGroupDTO>> getFarmGroupByIds(@RequestParam("partners") List<Long> partners) {
		List<FarmGroupDTO> resp = farmGroupService.getByIds(partners);
		ResponseEntity<List<FarmGroupDTO>> partnerResp = new ResponseEntity<List<FarmGroupDTO>>(resp, HttpStatus.OK);
		return partnerResp;
	}

	@RequestMapping(value = "/farm-group/delete")
	public ResponseEntity<BrandDTO> deleteById(HttpServletRequest request, @RequestParam(name = "id") Long id)
			throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		farmGroupService.deleteBrand(id,userId);
		return new ResponseEntity<BrandDTO>(HttpStatus.OK);
	}

}
