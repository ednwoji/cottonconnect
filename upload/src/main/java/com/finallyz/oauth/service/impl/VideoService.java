package com.finallyz.oauth.service.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finallyz.oauth.service.UploadService;
import com.finallyz.oauth.service.domain.Brand;
import com.finallyz.oauth.service.domain.Country;
import com.finallyz.oauth.service.domain.Video;
import com.finallyz.oauth.service.dto.VideoDTO;
import com.finallyz.oauth.service.repo.BrandRepository;
import com.finallyz.oauth.service.repo.CountryRepository;
import com.finallyz.oauth.service.repo.FarmGroupRepository;
import com.finallyz.oauth.service.repo.LearnerGroupRepository;
import com.finallyz.oauth.service.repo.ProgrammeRepository;
import com.finallyz.oauth.service.repo.VideoRepository;

@Service
public class VideoService {

	@Autowired
	private VideoRepository videoPagedRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private LearnerGroupRepository learnerGroupRepository;

	@Autowired
	private FarmGroupRepository farmGroupRepository;

	@Autowired
	private ProgrammeRepository programRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private BrandRepository brandRepository;

	public Video saveVideo(VideoDTO videoDTO, List<File> videoFiles) {
		Video video = new Video();
		if (videoDTO.getId() != null) {
			Optional<Video> foundVideo = videoPagedRepository.findById(videoDTO.getId());
			if (foundVideo.isPresent()) {
				video = foundVideo.get();
				
			}
		}
		if (videoDTO != null) {
			if (videoDTO.getId() != null) {
				video.setId(videoDTO.getId());
			}
			video.setDescription(videoDTO.getDescription());
			video.setName(videoDTO.getName());
		}

		if (videoDTO.getLearners() != null) {
			video.setLearnerGroups(learnerGroupRepository.findByIdIn(videoDTO.getLearners()));
		}

		if (videoDTO.getFarmGroups() != null) {
			video.setFarmGroups(farmGroupRepository.findByIdIn(videoDTO.getFarmGroups()));
		}

		if (videoDTO.getPrograms() != null) {
			video.setProgrammes(programRepository.findByIdIn(videoDTO.getPrograms()));
		}

		if (videoDTO.getCountries() != null) {
			List<Country> countryList = countryRepository.findByIdIn(videoDTO.getCountries());
			video.setCountries(countryList);
		}

		if (videoDTO.getBrands() != null) {
			List<Brand> brandList = brandRepository.findByIdIn(videoDTO.getBrands());
			video.setBrands(brandList);
		}

		if (videoDTO.getLink() != null) {
			video.setLink(videoDTO.getLink());
		}

		video.setType(videoDTO.getType());
		videoPagedRepository.save(video);

		if (videoFiles != null && videoFiles.size() > 0) {
			uploadService.saveVideo(videoFiles, video);
		}

		return video;
	}

}
