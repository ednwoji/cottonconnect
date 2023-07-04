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

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.TalukDTO;
import com.cottonconnect.elearning.ELearning.service.TalukService;

@RestController
@RequestMapping(value = "/location/taluk")
public class TalukController {

	@Autowired
	TalukService talukService;

	@RequestMapping(value = "/saveTaluk")
	public ResponseEntity<TalukDTO> saveDistrict(@RequestBody TalukDTO talukDTO) {
		TalukDTO resp = talukService.saveTaluk(talukDTO);
		ResponseEntity<TalukDTO> talukRep = new ResponseEntity<TalukDTO>(resp, HttpStatus.CREATED);
		return talukRep;

	}

	@RequestMapping(value = "/getAllTaluks")
	public ResponseEntity<TableResponse> getAllTaluks(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse stateList = talukService.getAllTaluks(draw, start, length, search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getTaluksByDistrict")
	public ResponseEntity<List<TalukDTO>> getTaluksByDistrict(@RequestParam("districtId") Long districtId) {
		List<TalukDTO> talukList = talukService.getTaluks(districtId);
		ResponseEntity<List<TalukDTO>> response = new ResponseEntity<List<TalukDTO>>(talukList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id/{id}")
	public ResponseEntity<TalukDTO> byId(@PathVariable("id") Long id) {
		TalukDTO resp = talukService.getById(id);
		ResponseEntity<TalukDTO> districtResp = new ResponseEntity<TalukDTO>(resp, HttpStatus.CREATED);
		return districtResp;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<TalukDTO> deleteById(@RequestParam(name = "id") Long id) {
		talukService.delete(id);
		ResponseEntity<TalukDTO> response = new ResponseEntity<TalukDTO>(HttpStatus.OK);
		return response;
	}
}
