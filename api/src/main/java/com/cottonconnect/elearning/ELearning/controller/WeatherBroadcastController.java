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
import com.cottonconnect.elearning.ELearning.dto.VideoDTO;
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
			WeatherBroadcastDTO broadcastReq,
			@RequestParam("video") MultipartFile video,
			@RequestParam("audio") MultipartFile audio,
			@RequestParam("document") MultipartFile document,
			HttpServletResponse res) throws IOException, CustomException {

		String videoUrl = null;
		String audioUrl = null;
		String documentUrl = null;

		if (!video.getOriginalFilename().isEmpty()) {
			File videoFile = uploadService.getFile(video);
			videoUrl = uploadService.saveFileToS3(videoFile, s3Folder);
		}

		if (!audio.getOriginalFilename().isEmpty()) {
			File audioFile = uploadService.getFile(audio);
			audioUrl = uploadService.saveFileToS3(audioFile, s3Folder);
		}
		if (!document.getOriginalFilename().isEmpty()) {
			File documentFile = uploadService.getFile(document);
			documentUrl = uploadService.saveFileToS3(documentFile, s3Folder);
		}

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

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<WeatherBroadcastDTO> getRecord(
			@RequestParam(name = "id") Long id) {
		WeatherBroadcastDTO list = weatherBroadcastService.findById(id);
		return new ResponseEntity<WeatherBroadcastDTO>(list, HttpStatus.OK);
	}

}
