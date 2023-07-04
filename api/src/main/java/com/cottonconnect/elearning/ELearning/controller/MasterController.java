package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.SeasonDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.Category;
import com.cottonconnect.elearning.ELearning.model.SubCategory;
import com.cottonconnect.elearning.ELearning.service.BrandService;
import com.cottonconnect.elearning.ELearning.service.FarmGroupService;
import com.cottonconnect.elearning.ELearning.service.KnowleldgeCenterService;
import com.cottonconnect.elearning.ELearning.service.LearnerGroupService;
import com.cottonconnect.elearning.ELearning.service.LocalPartnerService;
import com.cottonconnect.elearning.ELearning.service.ProgrammeService;
import com.cottonconnect.elearning.ELearning.service.SeasonService;

@RestController
@RequestMapping(value = "/master")
public class MasterController {
	@Autowired
	SeasonService seasonService;

	@Autowired
	BrandService brandService;

	@Autowired
	ProgrammeService programmeService;

	@Autowired
	LocalPartnerService localPartnerService;

	@Autowired
	FarmGroupService farmGroupService;

	@Autowired
	LearnerGroupService learnerGroupService;

	@Autowired
	KnowleldgeCenterService knowleldgeCenterService;

	@RequestMapping(value = "/season/saveSeason")
	public ResponseEntity<SeasonDTO> saveSeason(@RequestBody SeasonDTO seasonDTO) {
		SeasonDTO resp = seasonService.saveSeason(seasonDTO);
		ResponseEntity<SeasonDTO> seasonResp = new ResponseEntity<SeasonDTO>(resp, HttpStatus.CREATED);
		return seasonResp;
	}

	@RequestMapping(value = "/sub-categories/save")
	public void saveSubCategory(HttpServletRequest request, @RequestBody List<SubCategory> categories)
			throws CustomException {
		knowleldgeCenterService.saveSubCategory(categories);
	}

	@RequestMapping(value = "/sub-categories/list")
	public ResponseEntity<List<SubCategory>> getSubCategoryList(HttpServletRequest request) throws CustomException {
		List<SubCategory> list = knowleldgeCenterService.getSubCategoryList().stream().map(subCategory -> {
			SubCategory sc = new SubCategory();
			sc.setId(subCategory.getId());
			sc.setName(subCategory.getName());
			return subCategory;
		}).collect(Collectors.toList());

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/save")
	public void saveCategory(HttpServletRequest request, @RequestBody List<Category> categories)
			throws CustomException {
		knowleldgeCenterService.saveCategory(categories);
	}
}
