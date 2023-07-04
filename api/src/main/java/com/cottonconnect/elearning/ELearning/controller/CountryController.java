package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.CountryService;

@RestController
@RequestMapping(value = "/location/country")
public class CountryController {

	@Autowired
	CountryService countryService;

	@RequestMapping(value = "/saveCountry")
	public ResponseEntity<CountryDTO> save(HttpServletRequest request, @RequestBody @Valid CountryDTO country) throws CustomException {
		CountryDTO resp = countryService.saveCountry(country);
		ResponseEntity<CountryDTO> countryResp = new ResponseEntity<CountryDTO>(resp, HttpStatus.CREATED);
		
		String uri = request.getScheme() + "://" +   // "http" + "://
	             request.getServerName() +       // "myhost"
	             ":" + request.getServerPort() + // ":" + "8080"
	             request.getRequestURI() +       // "/people"
	            (request.getQueryString() != null ? "?" +
	             request.getQueryString() : ""); 
		
		
		
		return countryResp;
	}

	@RequestMapping(value = "/getAllCountries")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse countryList = countryService.getAllCountries(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(countryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getCountries")
	public ResponseEntity<List<CountryDTO>> getCountries() {
		List<CountryDTO> countryList = countryService.getCountries();
		ResponseEntity<List<CountryDTO>> response = new ResponseEntity<List<CountryDTO>>(countryList, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/by-id/{id}")
	public ResponseEntity<CountryDTO> byId(@PathVariable("id") Long id) {
		CountryDTO resp = countryService.getById(id);
		ResponseEntity<CountryDTO> countryResp = new ResponseEntity<CountryDTO>(resp, HttpStatus.CREATED);
		return countryResp;
	}
	
	@RequestMapping(value = "/delete")
	public ResponseEntity<CountryDTO> deleteById(@RequestParam(name = "id") Long id){
		countryService.delete(id);
		ResponseEntity<CountryDTO> response = new ResponseEntity<CountryDTO>( HttpStatus.OK);
		return response;
	}
}
