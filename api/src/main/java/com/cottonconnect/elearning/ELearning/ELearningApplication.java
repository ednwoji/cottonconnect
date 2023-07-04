package com.cottonconnect.elearning.ELearning;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.cottonconnect.elearning.ELearning.service.UploadService;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class ELearningApplication implements CommandLineRunner {
	@Resource
	UploadService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ELearningApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}

}
