package com.cottonconnect.elearning.ELearning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/delete")
public class DeleteController {
	@GetMapping("/")
	public void deleteAll() {
		
	}
}
