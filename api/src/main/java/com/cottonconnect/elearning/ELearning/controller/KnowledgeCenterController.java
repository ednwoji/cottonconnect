package com.cottonconnect.elearning.ELearning.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.dto.KnowledgeCenterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.cottonconnect.elearning.ELearning.service.KnowleldgeCenterService;

@RestController
@RequestMapping(value = "/service/knowledge-center")
public class KnowledgeCenterController {

	private final static Logger LOGGER = Logger.getLogger(KnowleldgeCenterService.class.getName());

	@Autowired
	private KnowleldgeCenterService knowleldgeCenterService;

	@Value(value = "${image.storage.path}")
	private String filePath;

	private final Path root = Paths.get("uploads");

	@RequestMapping(value = "/save")
	public ResponseEntity<KnowledgeCenterDTO> save(KnowledgeCenterDTO knowledgeCenterDTO,
			@RequestParam("file") MultipartFile[] files, HttpServletResponse res) throws IOException {
		List<File> imageFiles = new ArrayList<File>();
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
						imageFiles.add(imageFile);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		KnowledgeCenterDTO respDTO = knowleldgeCenterService.saveKnowledgeCenter(knowledgeCenterDTO, imageFiles);
		ResponseEntity<KnowledgeCenterDTO> response = new ResponseEntity<KnowledgeCenterDTO>(respDTO, HttpStatus.OK);
		LOGGER.info("Knowledge Center saved.");
		res.sendRedirect(knowledgeCenterDTO.getRedirectUrl());
		return response;
	}

	@RequestMapping(value = "/getAllRecords/{catId}")
	public ResponseEntity<TableResponse> getAllRecords(
			@PathVariable("catId") Long catId,
			@RequestParam(name = "draw") Integer draw,
			@RequestParam(name = "type") Long type,
			@RequestParam(defaultValue = "0") Integer start,
			@RequestParam(defaultValue = "10") Integer length,
			@RequestParam(name = "search[value]") String search) {

		TableResponse stateList = knowleldgeCenterService.getAllRecords(
				draw,
				start,
				length,
				catId,
				type,
				search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-category")
	public ResponseEntity<List<KnowledgeCenterDTO>> getKCByCategory(@RequestParam("category") String category) {
		List<KnowledgeCenterDTO> knowledgeCentreList = knowleldgeCenterService
				.findAllBySubCategory(Long.valueOf(category));
		ResponseEntity<List<KnowledgeCenterDTO>> response = new ResponseEntity<List<KnowledgeCenterDTO>>(
				knowledgeCentreList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-category-mobile")
	public ResponseEntity<List<KnowledgeCenterDTO>> getKCByCategoryMobile(@RequestParam("category") String category,
			@RequestParam("mobile") String mobile) {
		List<KnowledgeCenterDTO> knowledgeCentreList = knowleldgeCenterService
				.findAllBySubCategory(Long.valueOf(category), mobile);
		ResponseEntity<List<KnowledgeCenterDTO>> response = new ResponseEntity<List<KnowledgeCenterDTO>>(
				knowledgeCentreList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id")
	public ResponseEntity<KnowledgeCenterDTO> getKCById(@RequestParam("id") String id) {
		KnowledgeCenterDTO knowledgeCentreDTO = knowleldgeCenterService.findById(Long.valueOf(id));
		ResponseEntity<KnowledgeCenterDTO> response = new ResponseEntity<KnowledgeCenterDTO>(knowledgeCentreDTO,
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getPhoto", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> getPhoto(@RequestParam("photo") String photo,
			HttpServletResponse response) throws IOException {
		FileInputStream image = knowleldgeCenterService.getPhoto(photo);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(image));
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<KnowledgeCenterDTO> deleteById(@RequestParam(name = "id") Long id) {
		knowleldgeCenterService.delete(id);
		ResponseEntity<KnowledgeCenterDTO> response = new ResponseEntity<KnowledgeCenterDTO>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/deleteImage")
	public ResponseEntity<KnowledgeCenterDTO> deleteByImageId(@RequestParam(name = "id") Long id) {
		knowleldgeCenterService.deleteByImageId(id);
		ResponseEntity<KnowledgeCenterDTO> response = new ResponseEntity<KnowledgeCenterDTO>(HttpStatus.OK);
		return response;
	}
}
