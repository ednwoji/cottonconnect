package com.cottonconnect.elearning.ELearning.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.dto.CICRBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.CICRBroadcastService;
import com.cottonconnect.elearning.ELearning.service.UploadService;

@RestController
@RequestMapping(value = "/cicrBroadcast")
public class CICRBroadcastController {

	@Autowired
	private CICRBroadcastService cicrBroadcastService;

	@Autowired
	private UploadService uploadService;

	private String s3Folder = "cicr";

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CICRBroadcastDTO> save(
			@RequestParam("video") MultipartFile video,
			@RequestParam("audio") MultipartFile audio,
			@RequestParam("document") MultipartFile document,
			@RequestParam("country") Long country,
			@RequestParam("state") Long state,
			@RequestParam("district") Long district,
			@RequestParam(value = "taluk", required = false) Long taluk,
			@RequestParam(value = "village", required = false) Long village,
			@RequestParam("redirectUrl") String redirectUrl,
			CICRBroadcastDTO broadcastReq,
			HttpServletResponse res) throws IOException, CustomException {

//		File videoFile = uploadService.getFile(video);
//		File audioFile = uploadService.getFile(audio);
//		File documentFile = uploadService.getFile(document);

		broadcastReq.setCountry(country);
		broadcastReq.setVillage(village);
		broadcastReq.setTaluk(taluk);
		broadcastReq.setDistrict(district);
		broadcastReq.setState(state);
		broadcastReq.setRedirectUrl(redirectUrl);

		String videoUrl = uploadService.saveFileToCpanel(video);
		String audioUrl = uploadService.saveFileToCpanel(audio);
		String documentUrl = uploadService.saveFileToCpanel(document);

		cicrBroadcastService.saveCICR(
				broadcastReq,
				videoUrl,
				audioUrl,
				documentUrl);
		res.sendRedirect(broadcastReq.getRedirectUrl());
		return null;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public ResponseEntity<TableResponse> getAllRecords() {

		TableResponse list = cicrBroadcastService.getAllRecords();
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(
				list,
				HttpStatus.OK);
		return response;
	}



	@RequestMapping(value = "/delete")
	public ResponseEntity<CICRBroadcastDTO> deleteById(@RequestParam(name = "id") Long id) {
		cicrBroadcastService.delete(id);
		ResponseEntity<CICRBroadcastDTO> response = new ResponseEntity<CICRBroadcastDTO>(HttpStatus.OK);
		return response;
	}

}
