package com.cottonconnect.elearning.ELearning.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.dto.AwsResponseDTO;
import com.cottonconnect.elearning.ELearning.dto.CICRBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.VideoDTO;
import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.cottonconnect.elearning.ELearning.model.Video;
import com.cottonconnect.elearning.ELearning.service.VideoService;

@RestController
@RequestMapping(value = "/video")
public class VideoController {

	@Autowired
	VideoService videoService;

	@Value(value = "${image.storage.path}")
	private String filePath;

	private final Path root = Paths.get("uploads");

	@RequestMapping(value = "/getAllRecords/{type}")
	public ResponseEntity<TableResponse> getAllRecords(
			@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start,
			@RequestParam(defaultValue = "10") Integer length,
			@PathVariable("type") Long type,
			@RequestParam(name = "search[value]") String search) {

		TableResponse videoList = videoService.getAllRecords(draw, start, length, type, search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(videoList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<Video> deleteById(@RequestParam(name = "id") Long id) {
		videoService.delete(id);
		ResponseEntity<Video> response = new ResponseEntity<Video>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/save")
	public ResponseEntity<Video> save(Video video, @RequestParam("file") MultipartFile[] files, HttpServletResponse res)
			throws IOException {
		List<File> videoFiles = new ArrayList<File>();
		try {
			for (MultipartFile file : files) {
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				if (!StringUtils.isEmpty(fileName)) {
					if (fileName.contains("..")) {
						throw new FileStorageException(
								"Sorry! Filename contains invalid path sequence " + System.nanoTime() + "_" + fileName);
					}
					Path targetLocation = this.root.resolve(System.nanoTime() + "_" + fileName);
					Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
					File imageFile = new File(targetLocation.toUri());
					if (imageFile.exists()) {
						videoFiles.add(imageFile);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		videoService.saveVideo(video, videoFiles);
		ResponseEntity<Video> response = new ResponseEntity<Video>(video, HttpStatus.OK);
		res.sendRedirect(video.getRedirectUrl());
		return response;
	}

	@RequestMapping(value = "/save-video-path")
	public ResponseEntity<String> saveVideoPath(@RequestParam("file_name") String fileName,
			@RequestParam("path") String path, HttpServletResponse res) throws IOException {
		AwsResponseDTO awsReponse = new AwsResponseDTO();
		awsReponse.setFile_name(fileName);
		awsReponse.setPath(path);
		videoService.saveVideoPath(awsReponse);
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getAllVideos")
	public ResponseEntity<List<Video>> getAllVideos(
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "roleId", required = false) Long role) {
		Long type = Long.valueOf(1);
		if(role != null && role == 5) {
			type = Long.valueOf(2);
		}
		List<Video> videoList = videoService.getAllVideos(mobile, type);
		ResponseEntity<List<Video>> response = new ResponseEntity<List<Video>>(
				videoList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id")
	public ResponseEntity<VideoDTO> findById(@RequestParam("id") Long id) {
		VideoDTO video = videoService.findById(id);
		return new ResponseEntity<VideoDTO>(video, HttpStatus.OK);

	}
}
