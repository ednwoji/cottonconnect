package com.cottonconnect.elearning.ELearning.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.cottonconnect.elearning.ELearning.configuration.EmailUtility;
import com.cottonconnect.elearning.ELearning.dto.AwsResponseDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.VideoDTO;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Video;
import com.cottonconnect.elearning.ELearning.repo.VideoRepository;
import com.cottonconnect.elearning.ELearning.repo.page.VideoDocsPagedRepository;
import com.cottonconnect.elearning.ELearning.repo.page.VideoPagedRepository;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoPagedRepository videoPagedRepository;

	@Autowired
	UploadService uploadService;

	@Autowired
	VideoDocsPagedRepository videoDocsPagedRepository;

	@Autowired
	private VideoRepository videoRepository;

	private final static Logger LOGGER = Logger.getLogger(VideoServiceImpl.class.getName());

	@Override
	public TableResponse getAllRecords(Integer draw, Integer pageNo, Integer pageSize, Long type, String search) {

		TableResponse response = null;
		List<List<Object>> videoDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);

		Page<Video> videoPaged = videoPagedRepository.findAllWithPage(type, search.toLowerCase(), paging);
		if (videoPaged.hasContent()) {
			List<Video> videoList = videoPaged.getContent();
			videoDtoList = videoList.stream().map(video -> {
				List<Object> videoObjList = new ArrayList<>();

				videoObjList
						.add("<a class='detail' href='videodetail.jsp?id=" + video.getId() + "'>" + video.getName());

				videoObjList.add(video.getDescription() != null
						? video.getDescription().length() > 50 ? video.getDescription().substring(0, 50) + "..more"
								: video.getDescription()
						: "");
				videoObjList.add("<button class='btn btn-success btn-sm simple-icon-pencil' onclick=editz("
						+ video.getId()
						+ ")></button> <button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
						+ video.getId() + "')></button>");
				return videoObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, videoPaged.getTotalElements(), videoPaged.getTotalElements(),
					videoDtoList);
		} else {
			response = new TableResponse(draw, videoPaged.getTotalElements(), videoPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public void saveVideo(Video video, List<File> imageFiles) {
		videoPagedRepository.save(video);
		EmailUtility.sendEmail();
		uploadService.saveVideo(imageFiles, video);

	}

	@Override
	public void delete(Long id) {
		Optional<Video> video = videoPagedRepository.findById(id);
		if (video.isPresent()) {
			if (video.get().getDocuments() != null) {
				videoDocsPagedRepository.deleteAll(video.get().getDocuments());
			}
			videoPagedRepository.delete(video.get());
		}
	}

	@Override
	public void saveVideoPath(AwsResponseDTO awsReponse) {
		LOGGER.info("******************************************************");
		LOGGER.info("Video Name : " + awsReponse.getFile_name());
		LOGGER.info("Video Path : " + awsReponse.getPath());
		LOGGER.info("******************************************************");

		Video video = videoRepository.findByUrlContainingIgnoreCase(awsReponse.getFile_name());
		if (video != null) {
			String path = awsReponse.getPath().replace("s3://", "https://").replace("cotton-connect-images-dev",
					"cotton-connect-images-dev.s3.ap-south-1.amazonaws.com");
			video.setUrl(path);
			video.setStatus("Completed");
			videoRepository.save(video);
		}
	}

	@Override
	public List<Video> getAllVideos(String mobile, Long type) {

		if (type != null) {
			Video video = new Video();
			video.setType(type);
			return videoRepository.findAll(Example.of(video));
		}
		return videoRepository.findAll();
	}

	@Override
	public VideoDTO findById(Long id) {
		VideoDTO videoDTO = new VideoDTO();
		Optional<Video> videoOpt = videoRepository.findById(id);
		if (videoOpt.isPresent()) {
			Video video = videoOpt.get();

			List<Long> learnerList = video.getLearnerGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> farmGroupList = video.getFarmGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> programList = video.getProgrammes().stream().map(lg -> lg.getId()).collect(Collectors.toList());
			List<Long> countriesList = video.getCountries().stream().map(country -> country.getId())
					.collect(Collectors.toList());
			List<Long> brandList = video.getBrands().stream().map(brand -> brand.getId()).collect(Collectors.toList());

			List<Long> partnerList = new ArrayList<>();
			for (FarmGroup fg : video.getFarmGroups()) {
				partnerList.add(fg.getLocalPartner().getId());
			}

			videoDTO.setId(video.getId());
			videoDTO.setCountries(countriesList);
			videoDTO.setBrands(brandList);
			videoDTO.setDescription(video.getDescription());
			videoDTO.setPrograms(programList);
			videoDTO.setLocalPartners(partnerList);
			videoDTO.setFarmGroups(farmGroupList);
			videoDTO.setLearners(learnerList);
			videoDTO.setLink(video.getLink());
			videoDTO.setName(video.getName());
			videoDTO.setDescription(video.getDescription());
			videoDTO.setUrl(video.getUrl());
			videoDTO.setSourceUrl(video.getSourceUrl());
		}
		return videoDTO;
	}

}
