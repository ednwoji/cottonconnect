package com.cottonconnect.elearning.ELearning.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
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

import com.cottonconnect.elearning.ELearning.dto.EntryDTO;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.dto.MessageDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.cottonconnect.elearning.ELearning.model.FAQ;
import com.cottonconnect.elearning.ELearning.model.FaqQuery;
import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;
import com.cottonconnect.elearning.ELearning.repo.FaqQueryRepository;
import com.cottonconnect.elearning.ELearning.service.FAQService;
import com.cottonconnect.elearning.ELearning.service.UploadService;

@RequestMapping("/faq")
@RestController
public class FAQController {

	private final Path root = Paths.get("uploads");

	@Autowired
	FAQService faqService;

	@Autowired
	UploadService uploadService;

	@Autowired
	private FaqQueryRepository faqQueryRepository;

	@RequestMapping(value = "/save")
	public ResponseEntity<FAQ> save(HttpServletRequest request, FAQ faqList, HttpServletResponse res)
			throws CustomException, IOException {
		FAQ faqResp = faqService.saveFaq(faqList);
		res.sendRedirect(faqList.getRedirectUrl());
		return new ResponseEntity<FAQ>(faqResp, HttpStatus.OK);

	}

	@RequestMapping(value = "/query/save")
	public ResponseEntity<FaqQueryDTO> saveQuery(HttpServletRequest request, @RequestBody FaqQueryDTO faqQueryDTO)
			throws CustomException {

		FaqQueryDTO faqResp = faqService.saveFaqQuery(faqQueryDTO);
		return new ResponseEntity<FaqQueryDTO>(faqResp, HttpStatus.OK);
	}

	@RequestMapping(value = "/query/list")
	public ResponseEntity<TableResponse> getAllQueries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,
			@RequestParam(name = "search[value]") String search
		) {

		TableResponse faqQueryList = faqService.getAllFaqQueries(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(faqQueryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/question/list")
	public ResponseEntity<TableResponse> getAllFAQ(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse faqQueryList = faqService.getAllQuestion(draw, start, length, search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(faqQueryList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/query/by-id")
	public ResponseEntity<FaqQueryDTO> byId(@RequestParam(name = "id") Long id) {
		FaqQueryDTO queryDTO = faqService.findById(id);
		ResponseEntity<FaqQueryDTO> response = new ResponseEntity<FaqQueryDTO>(queryDTO, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/faq/by-id")
	public ResponseEntity<FAQ> faqById(@RequestParam(name = "id") Long id) {
		FAQ faq = faqService.faqById(id);
		ResponseEntity<FAQ> response = new ResponseEntity<FAQ>(faq, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/query/question/list")
	public ResponseEntity<List<EntryDTO>> getFaqQuestionList() {
		List<EntryDTO> entryDTOList = faqService.getFaqQuestionList();
		ResponseEntity<List<EntryDTO>> response = new ResponseEntity<List<EntryDTO>>(entryDTOList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/response/delete")
	public ResponseEntity<String> deleteQueryResponse(@RequestParam(name = "id") Long id) {
		faqService.deleteQueryResponse(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/query/by-farmer")
	public ResponseEntity<List<FaqQueryDTO>> getByFarmer(@RequestParam(name = "farmerId") String farmerId) {
		List<FaqQueryDTO> queryList = faqService.getByFarmer(farmerId);
		return new ResponseEntity<List<FaqQueryDTO>>(queryList, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveResp")
	public ResponseEntity<MessageDTO> saveFAQResponse(FaqQueryResponse faqResponse,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse res) throws IOException {
		MessageDTO msg = new MessageDTO(200, "Success");
		try {
			if (file != null) {
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
			} else {
				Optional<FaqQuery> faqOpt = faqQueryRepository.findById(faqResponse.getQueryId());
				if (faqOpt.isPresent()) {
					uploadService.saveResponseVoice(null, faqResponse, faqOpt.get(), faqResponse.getUser());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MessageDTO>(msg, HttpStatus.OK);
	}
}
