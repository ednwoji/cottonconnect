package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.FarmGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.LearnerGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.LearnerGroupService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/master")
public class LearnerGroupController {
	@Autowired
	LearnerGroupService learnerGroupService;

	@RequestMapping(value = "/farm-group/getAllLearners")
	public ResponseEntity<TableResponse> getAllLearners(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse countryList = learnerGroupService.getAllLearners(draw, start, length, search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/learner-group/save")
	public ResponseEntity<LearnerGroupDTO> saveLearnerGroup(HttpServletRequest request,
			@RequestBody LearnerGroupDTO learnerGroupDTO) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		LearnerGroupDTO resp = learnerGroupService.saveLearnerGroup(learnerGroupDTO, userId);
		ResponseEntity<LearnerGroupDTO> learnerGroupResp = new ResponseEntity<LearnerGroupDTO>(resp,
				HttpStatus.CREATED);
		return learnerGroupResp;
	}

	@RequestMapping(value = "/learner/by-fg")
	public ResponseEntity<List<LearnerGroupDTO>> getLearnerGroupByFG(HttpServletRequest request,
			@RequestParam("fg") Long fg) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<LearnerGroupDTO> list = learnerGroupService.getLearnerGroupByFG(fg);
		return new ResponseEntity<List<LearnerGroupDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/learner/by-fgs")
	public ResponseEntity<List<LearnerGroupDTO>> getLearnerGroupByFGs(HttpServletRequest request,
			@RequestParam("fg") List<Long> fg) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<LearnerGroupDTO> list = learnerGroupService.getLearnerGroupByFGs(fg);
		return new ResponseEntity<List<LearnerGroupDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/learner/by-id/{id}")
	public ResponseEntity<LearnerGroupDTO> getFarmGroupById(@PathVariable("id") Long id) {
		LearnerGroupDTO resp = learnerGroupService.getById(id);
		ResponseEntity<LearnerGroupDTO> partnerResp = new ResponseEntity<LearnerGroupDTO>(resp, HttpStatus.OK);
		return partnerResp;
	}
}
