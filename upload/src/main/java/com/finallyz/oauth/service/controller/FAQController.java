package com.finallyz.oauth.service.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.finallyz.oauth.service.UploadService;
import com.finallyz.oauth.service.domain.Device;
import com.finallyz.oauth.service.domain.FaqQuery;
import com.finallyz.oauth.service.domain.FaqQueryResponse;
import com.finallyz.oauth.service.dto.FaqQueryDTO;
import com.finallyz.oauth.service.dto.FaqResponseDTO;
import com.finallyz.oauth.service.dto.MessageDTO;
import com.finallyz.oauth.service.impl.FAQService;
import com.finallyz.oauth.service.repo.DeviceRepository;
import com.finallyz.oauth.service.repo.FaqQueryRepository;
import com.finallyz.oauth.service.repo.FaqResponseRepo;
import com.utility.Mapper;
import com.utility.WebUtils;

@RequestMapping("/faq")
@RestController
public class FAQController {
	@Autowired
	FAQService faqService;

	@Autowired
	private DeviceRepository deviceRepostiory;

	@Autowired
	private FaqQueryRepository faqQueryRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private FaqResponseRepo faqResponseRepo;

	private final Path root = Paths.get("uploads");

	@RequestMapping(value = "/query/save")
	public FaqQueryDTO saveFaqQuery(@RequestBody FaqQueryDTO faqQueryDTO) throws JsonProcessingException {
		FaqQuery faq = new FaqQuery();
		faq.setQuery(faqQueryDTO.getName());
		Device device = deviceRepostiory.findByImei(faqQueryDTO.getImei());
		if (device != null) {
			faq.setFarmer(device.getFarmer());
			faq.setImei(faqQueryDTO.getImei());
			faq.setQuery(faqQueryDTO.getName());
			Mapper.setAuditable(faq, faq.getFarmer().getName());
			faqQueryRepository.save(faq);
			uploadService.saveDocumet(faqQueryDTO, faq);
		}

		return faqQueryDTO;
	}

	@RequestMapping(value = "/saveResp")
	public ResponseEntity<MessageDTO> saveFAQResponse(FaqQueryResponse faqResponse,
			@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse res)
			throws IOException {
		MessageDTO msg = new MessageDTO(200, "Success");
		try {
			String fileName = StringUtils.cleanPath(System.currentTimeMillis() + file.getOriginalFilename());
			if (!StringUtils.isEmpty(fileName)) {
				if (fileName.contains("..")) {
					throw new FileStorageException(
							"Sorry! Filename contains invalid path sequence " + System.nanoTime() + "_" + fileName);
				}
				Path targetLocation = this.root.resolve(System.nanoTime() + "_" + fileName + ".weba");
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				File voiceFile = new File(targetLocation.toUri());
				Optional<FaqQuery> faqOpt = faqQueryRepository.findById(faqResponse.getQueryId());
				if (faqOpt.isPresent()) {
					uploadService.saveResponseVoice(voiceFile, faqResponse, faqOpt.get(), faqResponse.getUser());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MessageDTO>(msg, HttpStatus.OK);
	}

}
