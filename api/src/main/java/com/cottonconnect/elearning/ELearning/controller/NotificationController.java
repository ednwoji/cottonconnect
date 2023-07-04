package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.NotificationDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.service.NotificationService;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/save")
	public ResponseEntity<NotificationDTO> save(NotificationDTO notificationDTO, HttpServletResponse res)
			throws IOException {
		notificationService.save(notificationDTO, res);
		res.sendRedirect(notificationDTO.getRedirectUrl());
		return new ResponseEntity<NotificationDTO>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllRecords")
	public ResponseEntity<TableResponse> getAllRecords(@RequestParam(name = "draw") Integer draw,
			@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length,@RequestParam(name = "search[value]") String search) {

		TableResponse stateList = notificationService.getAllRecords(draw, start, length, 0L, search);
		ResponseEntity<TableResponse> response = new ResponseEntity<TableResponse>(stateList, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/by-id")
	public ResponseEntity<NotificationDTO> byId(@RequestParam("id") String id) {
		NotificationDTO notificationDTO = notificationService.findById(Long.valueOf(id));
		ResponseEntity<NotificationDTO> response = new ResponseEntity<NotificationDTO>(notificationDTO,
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getAllNotifications")
	public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestParam String mobileNumber) {
		List<NotificationDTO> notificationList = notificationService.getNotifications(mobileNumber);
		ResponseEntity<List<NotificationDTO>> response = new ResponseEntity<List<NotificationDTO>>(notificationList,
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<NotificationDTO> deleteNotification(@RequestParam(name = "id") Long id) {
		notificationService.delete(id);
		ResponseEntity<NotificationDTO> response = new ResponseEntity<NotificationDTO>(HttpStatus.OK);
		return response;
	}
}
