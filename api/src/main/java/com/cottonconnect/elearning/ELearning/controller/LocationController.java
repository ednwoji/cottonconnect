package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.StateDTO;
import com.cottonconnect.elearning.ELearning.service.CountryService;
import com.cottonconnect.elearning.ELearning.service.StateService;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	StateService stateService;
	
	@RequestMapping(value = "/getCountries")
	public ResponseEntity<List<CountryDTO>> getCountries() {
		List<CountryDTO> countryList = countryService.getCountries();
		ResponseEntity<List<CountryDTO>> response = new ResponseEntity<List<CountryDTO>>(countryList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/getStates")
	public ResponseEntity<List<StateDTO>> getStates() {
		List<StateDTO> stateList = stateService.getStates();
		ResponseEntity<List<StateDTO>> response = new ResponseEntity<List<StateDTO>>(stateList, HttpStatus.OK);
		return response;
	}
	
}
