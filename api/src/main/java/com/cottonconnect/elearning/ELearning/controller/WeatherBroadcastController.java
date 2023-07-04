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

import com.cottonconnect.elearning.ELearning.dto.WeatherBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.UploadService;
import com.cottonconnect.elearning.ELearning.service.WeatherBroadcastService;

@RestController
@RequestMapping(value = "/weatherBroadcast")
public class WeatherBroadcastController {

	@Autowired
	private WeatherBroadcastService weatherBroadcastService;

	@Autowired
	private UploadService uploadService;

	private String s3Folder = "weather";

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<WeatherBroadcastDTO> save(
			@RequestParam("video") MultipartFile video,
			@RequestParam("audio") MultipartFile audio,
			@RequestParam("document") MultipartFile document,
			@RequestParam("country") Long country,
			@RequestParam("state") Long state,
			@RequestParam("district") Long district,
			@RequestParam(value = "taluk", required = false) Long taluk,
			@RequestParam(value = "village", required = false) Long village,
			@RequestParam("redirectUrl") String redirectUrl,
			WeatherBroadcastDTO broadcastReq,
			HttpServletResponse res) throws IOException, CustomException {

//		File videoFile = uploadService.getFile(video);
//		File audioFile = uploadService.getFile(audio);
//		File documentFile = uploadService.getFile(document);

		broadcastReq.setCountry(country);
		broadcastReq.setVillage(village);
		broadcastReq.setDistrict(district);
		broadcastReq.setTaluk(taluk);
		broadcastReq.setState(state);
		broadcastReq.setRedirectUrl(redirectUrl);


		String videoUrl = uploadService.saveFileToCpanel(video);
		String audioUrl = uploadService.saveFileToCpanel(audio);
		String documentUrl = uploadService.saveFileToCpanel(document);

		weatherBroadcastService.saveWeather(
				broadcastReq,
				videoUrl,
				audioUrl,
				documentUrl);
		res.sendRedirect(broadcastReq.getRedirectUrl());
		return null;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public ResponseEntity<TableResponse> getAllRecords() {

		TableResponse list = weatherBroadcastService.getAllRecords();
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(
				list,
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<WeatherBroadcastDTO> deleteById(@RequestParam(name = "id") Long id) {
		weatherBroadcastService.delete(id);
		ResponseEntity<WeatherBroadcastDTO> response = new ResponseEntity<WeatherBroadcastDTO>(HttpStatus.OK);
		return response;
	}

}
