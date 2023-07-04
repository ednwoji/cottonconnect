package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cottonconnect.elearning.ELearning.repo.EmailRepository;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(value = "/email")
@Slf4j
public class EmailController {
	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailService emailService;

//	@PostMapping("/save")
//	public ResponseEntity<Email> saveEmail(EmailDTO email, HttpServletResponse res) throws IOException {
//		log.info("Brand Payload is" +email.getBrand());
//		log.info("redirectUrl Payload is" +email.getRedirectUrl());
//		log.info("Country Payload is" +email.getCountry());
//
//
//
//		Email resp = emailService.save(email);
//		res.sendRedirect(email.getRedirectUrl());
//		return new ResponseEntity<Email>(resp, HttpStatus.ACCEPTED);
//	}


	@PostMapping("/save")
	public ResponseEntity<?> saveEmail(@RequestParam("countries") Long countries,
									   @RequestParam("brands") Long brands,
									   @RequestParam("programs") List<Long> programs,
									   @RequestParam("localPartners") List<Long> localPartners,
									   @RequestParam("farmGroups") List<Long> farmGroups,
									   @RequestParam("emails1") String emails,
									   @RequestParam("emails2") String emails2,
									   @RequestParam("emails3") String emails3,
									   @RequestParam("emails4") String emails4,
									   @RequestParam("emails5") String emails5,
									   @RequestParam("emails6") String emails6,
									   @RequestParam("learners") List<Long> learners,
									   @RequestParam("redirectUrl") String redirectUrl,
									   @RequestParam("id") Long id,
									   EmailDTO email, HttpServletResponse res) throws IOException, ServletException {

		try {
			log.info("Brand Payload is " + brands);
			log.info("redirectUrl Payload is " + redirectUrl);
			log.info("Country Payload is " + countries);
			log.info("LocalPartners Payload is " + localPartners.toString());
			log.info("ID is " + id);

			System.out.println(emails2);


			List<String> emailList = new ArrayList<>();
			if(!emails.equals("")) {
				emailList.add(emails);
			}

			if(!emails2.equals("")) {
				emailList.add(emails2);
			}
			if(!emails3.equals("")) {
				emailList.add(emails3);
			}
			if(!emails4.equals("")) {
				emailList.add(emails4);
			}
			if(!emails5.equals("")) {
				emailList.add(emails5);
			}
			if(!emails6.equals("")) {
				emailList.add(emails6);
			}

			email.setBrand(brands);
			email.setLearners(learners);
			email.setPrograms(programs);
			email.setCountry(countries);
			email.setFarmGroups(farmGroups);
			email.setLocalPartners(localPartners);
			email.setRedirectUrl(redirectUrl);
			email.setEmails(emailList);
			email.setId(id);

			log.info("Entire Email payload is " + email.toString());

			Email resp = emailService.save(email);
			res.sendRedirect("http://cottonconnectelearning.in:10000/emailmanagement.jsp?status=success");
//			res.sendRedirect("http://localhost:8080/emailmanagement.jsp?status=success");
			return new ResponseEntity<Email>(resp, HttpStatus.ACCEPTED);
		}

		catch(Exception e) {
			log.info(e.getMessage());
			res.sendRedirect("http://cottonconnectelearning.in:10000/emailmanagement.jsp?status=failed");
//			res.sendRedirect("http://localhost:8080/emailmanagement.jsp?status=failed");
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);

		}
	}

	@RequestMapping(value = "/getAllEmails")
	public ResponseEntity<TableResponse> getAllCountries(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {

		TableResponse emailList = emailService.getEmails(draw, start, length);
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
