package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.EmailDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Email;
import com.cottonconnect.elearning.ELearning.service.EmailService;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/save")
	public ResponseEntity<Email> saveEmail(EmailDTO email, HttpServletResponse res) throws IOException {
		Email resp = emailService.save(email);
		res.sendRedirect(email.getRedirectUrl());
		return new ResponseEntity<Email>(resp, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/getAllEmails")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse emailList = emailService.getEmails(draw, start, length,search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(emailList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id")
	public ResponseEntity<EmailDTO> getEmailById(@RequestParam("id") String id) {
		EmailDTO emailDTO = emailService.findById(Long.valueOf(id));
		ResponseEntity<EmailDTO> response = new ResponseEntity<EmailDTO>(emailDTO, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<EmailDTO> delete(@RequestParam("id") String id,@RequestParam("user") String user) {
		emailService.delete(Long.valueOf(id),user);
		ResponseEntity<EmailDTO> response = new ResponseEntity<EmailDTO>(HttpStatus.OK);
		return response;
	}
}
