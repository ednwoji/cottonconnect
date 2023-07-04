package com.cottonconnect.elearning.ELearning.service;

import java.io.File;
import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.AwsResponseDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.VideoDTO;
import com.cottonconnect.elearning.ELearning.model.Video;

public interface VideoService {
	TableResponse getAllRecords(Integer draw, Integer start, Integer length,Long type,String search);

	void saveVideo(Video video, List<File> imageFiles);

	void delete(Long id);

	void saveVideoPath(AwsResponseDTO awsReponse);

	List<Video> getAllVideos(String mobile,Long type);

	VideoDTO findById(Long id);
}
