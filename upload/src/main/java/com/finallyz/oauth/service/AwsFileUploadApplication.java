package com.finallyz.oauth.service;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsFileUploadApplication implements CommandLineRunner{
	
	@Resource
	UploadService storageService;

	public static void main(String[] args) {
		SpringApplication.run(AwsFileUploadApplication.class, args);
	}

	public void run(String... args) throws Exception {
		storageService.init();
	}
	
}
