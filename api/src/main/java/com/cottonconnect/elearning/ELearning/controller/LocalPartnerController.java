package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.LocalPartnerNameDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.LocalPartnerService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/master")
@Slf4j
public class LocalPartnerController {
	@Autowired
	LocalPartnerService localPartnerService;
	
	@RequestMapping(value = "/partner/save-local-partner")
	public ResponseEntity<LocalPartnerNameDTO> saveLocalPartner(HttpServletRequest request,
			@RequestBody LocalPartnerNameDTO localPartnerNameDTO, HttpServletResponse res)
			throws CustomException, IOException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		LocalPartnerNameDTO resp = localPartnerService.saveLocalPartner(localPartnerNameDTO, userId);
		ResponseEntity<LocalPartnerNameDTO> localPartnerResp = new ResponseEntity<LocalPartnerNameDTO>(resp,
				HttpStatus.CREATED);
		res.sendRedirect(localPartnerNameDTO.getRedirectUrl());
		return localPartnerResp;
	}

	@RequestMapping(value = "/partner/getAllPartners")
	public ResponseEntity<TableResponse> getAllPartners(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse countryList = localPartnerService.getAllPartners(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/partner/by-id/{id}")
	public ResponseEntity<LocalPartnerNameDTO> getPartnerById(@PathVariable("id") Long id) {
		LocalPartnerNameDTO resp = localPartnerService.getById(id);
		ResponseEntity<LocalPartnerNameDTO> partnerResp = new ResponseEntity<LocalPartnerNameDTO>(resp,
				HttpStatus.CREATED);
		return partnerResp;
	}

	@RequestMapping(value = "/partner/by-program")
	public ResponseEntity<List<LocalPartnerNameDTO>> getPartnerByProgram(HttpServletRequest request,
			@RequestParam("program") Long programId) {
		log.info("Program ID is "+programId);
		List<LocalPartnerNameDTO> partnerList = localPartnerService.getLocalPartner(programId);
		ResponseEntity<List<LocalPartnerNameDTO>> response = new ResponseEntity<List<LocalPartnerNameDTO>>(partnerList,
				HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/partner/by-programs")
	public ResponseEntity<List<LocalPartnerNameDTO>> getPartnerByProgram(HttpServletRequest request,
			@RequestParam("program") List<Long> programs) {

		log.info("Program ID is "+programs);
		List<LocalPartnerNameDTO> partnerList = localPartnerService.getLocalPartners(programs);
		ResponseEntity<List<LocalPartnerNameDTO>> response = new ResponseEntity<List<LocalPartnerNameDTO>>(partnerList,
				HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/partner/delete")
	public ResponseEntity<LocalPartnerNameDTO> deleteById(@RequestParam(name = "id") Long id){
		localPartnerService.delete(id);
		ResponseEntity<LocalPartnerNameDTO> response = new ResponseEntity<LocalPartnerNameDTO>( HttpStatus.OK);
		return response;
	}
}
