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

import com.cottonconnect.elearning.ELearning.dto.ProgrammeDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.BrandService;
import com.cottonconnect.elearning.ELearning.service.ProgrammeService;
import com.utility.WebUtils;

@RestController
@RequestMapping(value = "/master")
public class ProgramController {
	@Autowired
	BrandService brandService;
	
	@Autowired
	ProgrammeService programmeService;
	
	@RequestMapping(value = "/programme/getAllBrands")
	public ResponseEntity<TableResponse> getAllBrands(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {
		TableResponse brandList = brandService.getAllBrands(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(brandList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/program/by-id/{id}")
	public ResponseEntity<ProgrammeDTO> getProgramById(@PathVariable("id") Long id) {
		ProgrammeDTO resp = programmeService.getById(id);
		ResponseEntity<ProgrammeDTO> programResp = new ResponseEntity<ProgrammeDTO>(resp, HttpStatus.CREATED);
		return programResp;
	}
	
	@RequestMapping(value = "/program/delete")
	public ResponseEntity<ProgrammeDTO> deleteByProgramId(HttpServletRequest request,@RequestParam(name = "id") Long id) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		programmeService.deleteProgram(id);
		return new ResponseEntity<ProgrammeDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/programme/saveProgramme")
	public ResponseEntity<ProgrammeDTO> saveProgramme(HttpServletRequest request,
			@RequestBody ProgrammeDTO programmeDTO) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		ProgrammeDTO resp = programmeService.saveProgramme(programmeDTO, userId);
		ResponseEntity<ProgrammeDTO> programmeResp = new ResponseEntity<ProgrammeDTO>(resp, HttpStatus.CREATED);
		return programmeResp;
	}

	@RequestMapping(value = "/programme/getAllPrograms")
	public ResponseEntity<TableResponse> getAllPrograms(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse countryList = programmeService.getAllPrograms(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/programme/getPrograms")
	public ResponseEntity<List<ProgrammeDTO>> getPrograms(HttpServletRequest request) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<ProgrammeDTO> list = programmeService.getPrograms();
		return new ResponseEntity<List<ProgrammeDTO>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/programme/by-brand")
	public ResponseEntity<List<ProgrammeDTO>> getProgramByBrand(HttpServletRequest request,
			@RequestParam("brandId") Long brandId) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<ProgrammeDTO> list = programmeService.getProgramByBrand(brandId);
		return new ResponseEntity<List<ProgrammeDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/programme/by-brands")
	public ResponseEntity<List<ProgrammeDTO>> getProgramByBrands(HttpServletRequest request,
			@RequestParam("brandId") List<Long> brandId) throws CustomException {
		String userId = WebUtils.getHeadersInfo(request, "user-id");
		List<ProgrammeDTO> list = programmeService.getProgramByBrands(brandId);
		return new ResponseEntity<List<ProgrammeDTO>>(list, HttpStatus.OK);
	}
}
