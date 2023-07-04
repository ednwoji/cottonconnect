package com.finallyz.oauth.service.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.finallyz.oauth.service.domain.Video;
import com.finallyz.oauth.service.dto.VideoDTO;
import com.finallyz.oauth.service.impl.VideoService;

@RestController
@RequestMapping(value = "/video")
public class VideoController {
	@Autowired
	VideoService videoService;

	private final Path root = Paths.get("uploads");

	@RequestMapping(value = "/save")
	public ResponseEntity<Video> save(VideoDTO videoDTO, @RequestParam("file") MultipartFile[] files,
			HttpServletResponse res) throws IOException {
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

		Video video = videoService.saveVideo(videoDTO, videoFiles);
		ResponseEntity<Video> response = new ResponseEntity<Video>(video, HttpStatus.OK);
		res.sendRedirect(videoDTO.getRedirectUrl());
		return response;
	}

}
