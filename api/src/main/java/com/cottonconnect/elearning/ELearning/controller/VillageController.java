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
import com.cottonconnect.elearning.ELearning.dto.VillageDTO;
import com.cottonconnect.elearning.ELearning.service.VillageService;

@RestController
@RequestMapping(value = "/location/village")
public class VillageController {
	@Autowired
	VillageService villageService;

	@RequestMapping(value = "/saveVillage")
	public ResponseEntity<VillageDTO> saveVillage(@RequestBody VillageDTO villageDTO) {
		VillageDTO resp = villageService.saveVillage(villageDTO);
		ResponseEntity<VillageDTO> villageResp = new ResponseEntity<VillageDTO>(resp, HttpStatus.CREATED);
		return villageResp;
	}

	@RequestMapping(value = "/getAllVillages")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse stateList = villageService.getAllVillages(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getVillageByTaluk")
	public ResponseEntity<List<VillageDTO>> getVillageByTaluk(@RequestParam("talukId") Long talukId) {
		List<VillageDTO> talukList = villageService.getVillageByTaluk(talukId);
		ResponseEntity<List<VillageDTO>> response = new ResponseEntity<List<VillageDTO>>(talukList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id/{id}")
	public ResponseEntity<VillageDTO> byId(@PathVariable("id") Long id) {
		VillageDTO resp = villageService.getById(id);
		ResponseEntity<VillageDTO> villageResp = new ResponseEntity<VillageDTO>(resp, HttpStatus.CREATED);
		return villageResp;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<TalukDTO> deleteById(@RequestParam(name = "id") Long id) {
		villageService.delete(id);
		ResponseEntity<TalukDTO> response = new ResponseEntity<TalukDTO>(HttpStatus.OK);
		return response;
	}
}
