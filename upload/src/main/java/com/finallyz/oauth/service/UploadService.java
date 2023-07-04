package com.finallyz.oauth.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.finallyz.oauth.service.domain.FaqDocuments;
import com.finallyz.oauth.service.domain.FaqQuery;
import com.finallyz.oauth.service.domain.FaqQueryResponse;
import com.finallyz.oauth.service.domain.Video;
import com.finallyz.oauth.service.domain.VideoDocuments;
import com.finallyz.oauth.service.dto.Document;
import com.finallyz.oauth.service.dto.FaqQueryDTO;
import com.finallyz.oauth.service.repo.FaqDocumetsRepository;
import com.finallyz.oauth.service.repo.FaqResponseRepo;
import com.finallyz.oauth.service.repo.VideoDocsPagedRepository;
import com.finallyz.oauth.service.repo.VideoRepository;
import com.utility.Constants;
import com.utility.Mapper;

@Service
public class UploadService {
	@Value(value = "${accessKey}")
	private String accessKey;

	@Value(value = "${secretkey}")
	private String secretkey;

	@Value(value = "${image-bucket}")
	private String imageBucket;

	@Autowired
	private VideoRepository videoPagedRepository;

	@Autowired
	private VideoDocsPagedRepository videoDocsPagedRepository;

	@Autowired
	FaqDocumetsRepository faqDocumetsRepository;

	@Autowired
	FaqResponseRepo faqResponseRepo;

	private final Path root = Paths.get("uploads");

	public void init() {
		try {
			if (!Files.isDirectory(root)) {
				Files.createDirectory(root);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Async
	public void saveVideo(List<File> imageFiles, Video video) {
		try {
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

			AmazonS3 s3client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1)
					.build();

			List<VideoDocuments> videoDocsList = new ArrayList<>();

			for (File imageFile : imageFiles) {
				s3client.putObject(new PutObjectRequest(imageBucket, "file/" + imageFile.getName(), imageFile)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				URL url = s3client.getUrl(imageBucket, "file/" + imageFile.getName());

				video.setUrl(url.toString());
				video.setSourceUrl("https://cotton-connect-images-dev.s3.ap-south-1.amazonaws.com/file/"+imageFile.getName());
				VideoDocuments videoDocs = new VideoDocuments();
				videoDocs.setUrl(url.toString());
				videoDocs.setVideo(video);
				videoDocsList.add(videoDocs);
			}

			video.setStatus("Streaming in progress");
			video.setDocuments(videoDocsList);
			videoDocsPagedRepository.saveAll(videoDocsList);
			videoPagedRepository.save(video);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void saveResponseVoice(File voiceFile, FaqQueryResponse faqResponse, FaqQuery faqQuery, String userName) {
		if (voiceFile != null) {
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

			AmazonS3 s3client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1)
					.build();

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("audio/mpeg");
			s3client.putObject(new PutObjectRequest(imageBucket, "voice/" + voiceFile.getName(), voiceFile)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			URL url = s3client.getUrl(imageBucket, "voice/" + voiceFile.getName());
			faqResponse.setUrl(url.toString());
		}
		faqResponse.setFaqQuery(faqQuery);
		Mapper.setAuditable(faqResponse, userName);
		faqResponseRepo.save(faqResponse);
	}

	@Async
	public void saveDocumet(FaqQueryDTO faqQuery, FaqQuery faq) {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();

		List<FaqDocuments> docList = new ArrayList<FaqDocuments>();
		for (Document document : faqQuery.getDocuments()) {
			byte[] bI = Base64.getDecoder().decode(document.getContent());

			InputStream fis = new ByteArrayInputStream(bI);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(bI.length);
			metadata.setCacheControl("public, max-age=31536000");
			URL url = null;
			if (document.getDocType().equalsIgnoreCase(Constants.IMAGE)) {
				metadata.setContentType("image/png");
				s3client.putObject(imageBucket, "img/" + document.getFileName(), fis, metadata);
				s3client.setObjectAcl(imageBucket, "img/" + document.getFileName(), CannedAccessControlList.PublicRead);

				url = s3client.getUrl(imageBucket, "img/" + document.getFileName());
			} else if (document.getDocType().equalsIgnoreCase(Constants.VOICE)) {
				metadata.setContentType("audio/mpeg");
				s3client.putObject(imageBucket, "voice/" + document.getFileName(), fis, metadata);
				s3client.setObjectAcl(imageBucket, "voice/" + document.getFileName(),
						CannedAccessControlList.PublicRead);

				url = s3client.getUrl(imageBucket, "voice/" + document.getFileName());
			}

			FaqDocuments faqDoc = new FaqDocuments();
			faqDoc.setDocType(document.getDocType());
			faqDoc.setFaqQuery(faq);
			faqDoc.setUrl(url.toString());
			docList.add(faqDoc);
		}

		faqDocumetsRepository.saveAll(docList);
	}

}
