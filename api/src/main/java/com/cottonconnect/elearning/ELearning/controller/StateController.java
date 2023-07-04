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

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.StateDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.service.StateService;

@RestController
@RequestMapping(value = "/location/state")
public class StateController {
	@Autowired
	StateService stateService;

	@RequestMapping(value = "/saveState")
	public ResponseEntity<StateDTO> saveState(@RequestBody StateDTO state) {
		StateDTO resp = stateService.saveState(state);
		ResponseEntity<StateDTO> stateResp = new ResponseEntity<StateDTO>(resp, HttpStatus.CREATED);
		return stateResp;

	}

	@RequestMapping(value = "/getAllStates")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse stateList = stateService.getAllStates(draw, start, length);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getStatesByCountry")
	public ResponseEntity<List<StateDTO>> getCountries(@RequestParam("countryId") Long countryId) {
		List<StateDTO> stateList = stateService.getStates(countryId);
		ResponseEntity<List<StateDTO>> response = new ResponseEntity<List<StateDTO>>(stateList, HttpStatus.OK);
		return response;
	}
	
	
	@RequestMapping(value = "/by-id/{id}")
	public ResponseEntity<StateDTO> byId(@PathVariable("id") Long id) {
		StateDTO resp = stateService.getById(id);
		ResponseEntity<StateDTO> stateResp = new ResponseEntity<StateDTO>(resp, HttpStatus.CREATED);
		return stateResp;
	}
	
	@RequestMapping(value = "/delete")
	public ResponseEntity<CountryDTO> deleteById(@RequestParam(name = "id") Long id){
		stateService.delete(id);
		ResponseEntity<CountryDTO> response = new ResponseEntity<CountryDTO>( HttpStatus.OK);
		return response;
	}
}
