package com.cottonconnect.elearning.ELearning.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerExcelDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.FaqQuery;
import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;
import com.cottonconnect.elearning.ELearning.model.Video;

public interface UploadService {
	public void init();

	public void save(List<File> imageFiles, KnowledgeCenter knowledgeCenter);

	public void saveDocumet(FaqQueryDTO faqQuery, FaqQuery faq);

	public void saveVideo(List<File> imageFiles, Video video);

	public void saveResponseVoice(File voiceFile, FaqQueryResponse faqResponse, FaqQuery faqQuery, String userName);

	public void saveUploadFarmer(List<UploadFarmerExcelDTO> excelData, UploadFarmerDTO farmerDTO) throws CustomException;

	public String saveFileToS3(File file,String folder);

	public File getFile(MultipartFile file);

}
