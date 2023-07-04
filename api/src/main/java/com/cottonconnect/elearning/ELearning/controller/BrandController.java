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

import com.cottonconnect.elearning.ELearning.dto.BrandDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.BrandService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/master")
public class BrandController {

	@Autowired
	BrandService brandService;

	@RequestMapping(value = "/brand/saveBrand")
	public ResponseEntity<BrandDTO> saveBrand(HttpServletRequest request, @RequestBody BrandDTO brandDto)
			throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		BrandDTO resp = brandService.saveBrand(brandDto, userId);
		ResponseEntity<BrandDTO> seasonResp = new ResponseEntity<BrandDTO>(resp, HttpStatus.CREATED);
		return seasonResp;

	}

	@RequestMapping(value = "/brand/by-id/{id}")
	public ResponseEntity<BrandDTO> byId(@PathVariable("id") Long id) {
		BrandDTO resp = brandService.getById(id);
		ResponseEntity<BrandDTO> villageResp = new ResponseEntity<BrandDTO>(resp, HttpStatus.CREATED);
		return villageResp;
	}

	@RequestMapping(value = "/brand/country/{id}")
	public ResponseEntity<List<BrandDTO>> getBrandByCountry(@PathVariable("id") Long id) {
		List<BrandDTO> resp = brandService.getByCountryId(id);
		ResponseEntity<List<BrandDTO>> villageResp = new ResponseEntity<List<BrandDTO>>(resp, HttpStatus.CREATED);
		return villageResp;
	}
	
	@RequestMapping(value = "/brand/countries")
	public ResponseEntity<List<BrandDTO>> getBrandByCountries(HttpServletRequest request,@RequestParam("id") List<Long> id) {
		List<BrandDTO> resp = brandService.getByCountryIds(id);
		ResponseEntity<List<BrandDTO>> villageResp = new ResponseEntity<List<BrandDTO>>(resp, HttpStatus.CREATED);
		return villageResp;
	}
	
	@RequestMapping(value = "/brand/getBrands")
	public ResponseEntity<List<BrandDTO>> getBrands(HttpServletRequest request) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<BrandDTO> list = brandService.getBrands();
		return new ResponseEntity<List<BrandDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/brand/delete")
	public ResponseEntity<BrandDTO> deleteById(HttpServletRequest request,@RequestParam(name = "id") Long id) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		brandService.deleteBrand(id);
		return new ResponseEntity<BrandDTO>(HttpStatus.OK);
	}

}
