package com.cottonconnect.elearning.ELearning.repo.page;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cottonconnect.elearning.ELearning.dto.Document;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerExcelDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.exception.FileStorageException;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.District;
import com.cottonconnect.elearning.ELearning.model.FaqDocuments;
import com.cottonconnect.elearning.ELearning.model.FaqQuery;
import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Farmer;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenterImages;
import com.cottonconnect.elearning.ELearning.model.LearnerGroup;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.model.State;
import com.cottonconnect.elearning.ELearning.model.Taluk;
import com.cottonconnect.elearning.ELearning.model.Video;
import com.cottonconnect.elearning.ELearning.model.VideoDocuments;
import com.cottonconnect.elearning.ELearning.model.Village;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.DistrictRepository;
import com.cottonconnect.elearning.ELearning.repo.FaqDocumetsRepository;
import com.cottonconnect.elearning.ELearning.repo.FaqResponseRepo;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCenterRepo;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCentreImagesRepo;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.StateRepository;
import com.cottonconnect.elearning.ELearning.repo.TalukRepository;
import com.cottonconnect.elearning.ELearning.repo.VillageRepository;
import com.cottonconnect.elearning.ELearning.service.UploadService;
import com.jcabi.aspects.Cacheable;
import com.utility.Constants;
import com.utility.Mapper;

@Service
public class UploadServiceImpl implements UploadService {

	@Value(value = "${accessKey}")
	private String accessKey;

	@Value(value = "${secretkey}")
	private String secretkey;

	@Value(value = "${image-bucket}")
	private String imageBucket;

	private Map<String, Integer> funcCalls;
	private Map<String, List<String>> funcResultCache;

	private final Path root = Paths.get("uploads");

	@Autowired
	private KnowledgeCenterRepo knowledgeCenterRepo;

	@Autowired
	private KnowledgeCentreImagesRepo knowledgeCentreImagesRepo;

	@Autowired
	VideoDocsPagedRepository videoDocsPagedRepository;

	@Autowired
	VideoPagedRepository videoPagedRepository;

	@Autowired
	FaqDocumetsRepository faqDocumetsRepository;

	@Autowired
	FaqResponseRepo faqResponseRepo;

	@Autowired
	FarmerRepository farmerRepo;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	TalukRepository talukRepository;

	@Autowired
	VillageRepository villageRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	ProgrammeRepository programmeRepository;

	@Autowired
	FarmGroupRepository farmGroupRepository;

	@Autowired
	LearnerGroupRepository learnerGroupRepository;

	@Override
	public void init() {
		try {
			if (!Files.isDirectory(root)) {
				Files.createDirectory(root);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	@Async
	public void save(List<File> imageFiles, KnowledgeCenter knowledgeCenter) {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();

		List<KnowledgeCenterImages> kcImagesList = new ArrayList<>();

		for (File imageFile : imageFiles) {
			s3client.putObject(new PutObjectRequest(imageBucket, "img/" + imageFile.getName(), imageFile)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			URL url = s3client.getUrl(imageBucket, "img/" + imageFile.getName());

			KnowledgeCenterImages kcImgage = new KnowledgeCenterImages();
			kcImgage.setImageUrl(url.toString());
			kcImgage.setKnowledgeCenter(knowledgeCenter);
			kcImagesList.add(kcImgage);
		}

		knowledgeCenter.setKnowledgeCenterImages(kcImagesList);
		knowledgeCentreImagesRepo.saveAll(kcImagesList);
		knowledgeCenterRepo.save(knowledgeCenter);
	}

	@Async
	public void saveFile(MultipartFile[] files, KnowledgeCenter knowledgeCenter) {

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();

		List<KnowledgeCenterImages> kcImagesList = new ArrayList<>();

		for (MultipartFile file : files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			try {
				File convFile = File.createTempFile("cc_photos", fileName);
				convFile.createNewFile();
				file.transferTo(convFile);

				s3client.putObject(new PutObjectRequest(imageBucket,
						"img/" + System.currentTimeMillis() + "_" + convFile.getName(), convFile)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				URL url = s3client.getUrl(imageBucket, "img/" + System.currentTimeMillis() + "_" + convFile.getName());

				KnowledgeCenterImages kcImgage = new KnowledgeCenterImages();
				kcImgage.setImageUrl(url.toString());
				kcImgage.setKnowledgeCenter(knowledgeCenter);
				kcImagesList.add(kcImgage);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		knowledgeCenter.setKnowledgeCenterImages(kcImagesList);
		knowledgeCenterRepo.save(knowledgeCenter);

	}

	@Async
	@Override
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

	@Async
	@Override
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

				VideoDocuments videoDocs = new VideoDocuments();
				videoDocs.setUrl(url.toString());
				videoDocs.setVideo(video);
				videoDocsList.add(videoDocs);
			}

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

	@Override
	public void saveUploadFarmer(List<UploadFarmerExcelDTO> excelData, UploadFarmerDTO farmerDTO)
			throws CustomException {
		List<Farmer> farmersList = new ArrayList<Farmer>();
		Farmer farmer = new Farmer();

		if (farmerDTO.getCountry() != null) {
			Optional<Country> countryOpt = countryRepository.findById(farmerDTO.getCountry());
			if (countryOpt.isPresent())
				farmer.setCountry(countryOpt.get());
		}

		if (farmerDTO.getState() != null) {
			Optional<State> stateOpt = stateRepository.findById(farmerDTO.getState());
			if (stateOpt.isPresent())
				farmer.setState(stateOpt.get());
		}

		if (farmerDTO.getDistrict() != null) {
			Optional<District> districtOpt = districtRepository.findById(farmerDTO.getDistrict());
			if (districtOpt.isPresent())
				farmer.setDistrict(districtOpt.get());
		}

		if (farmerDTO.getTaluk() != null) {
			Optional<Taluk> talukOpt = talukRepository.findById(farmerDTO.getTaluk());
			if (talukOpt.isPresent())
				farmer.setTaluk(talukOpt.get());
		}

		if (farmerDTO.getVillage() != null) {
			Optional<Village> villageOpt = villageRepository.findById(farmerDTO.getVillage());
			if (villageOpt.isPresent())
				farmer.setVillage(null);
		}

		if (farmerDTO.getBrand() != null) {
			Optional<Brand> brandOpt = brandRepository.findById(farmerDTO.getBrand());
			if (brandOpt.isPresent())
				farmer.setBrand(brandOpt.get());
		}

		if (farmerDTO.getProgram() != null) {
			Optional<Programme> programOpt = programmeRepository.findById(farmerDTO.getProgram());
			if (programOpt.isPresent())
				farmer.setProgramme(programOpt.get());
		}

		if (farmerDTO.getFarmGroup() != null) {
			Optional<FarmGroup> farmerGroupOpt = farmGroupRepository.findById(farmerDTO.getFarmGroup());
			if (farmerGroupOpt.isPresent())
				farmer.setFarmGroup(farmerGroupOpt.get());
		}

		if (farmerDTO.getLearnerGroup() != null) {
			Optional<LearnerGroup> learnerGroupOpt = learnerGroupRepository.findById(farmerDTO.getLearnerGroup());
			if (learnerGroupOpt.isPresent())
				farmer.setLearnerGroup(learnerGroupOpt.get());
		}
		for (UploadFarmerExcelDTO row : excelData) {
			Farmer foundedFarmer = farmerRepo.findByMobileNumber(row.getFarmerMobileNumber());
			if (foundedFarmer != null) {
				throw new CustomException(row.getFarmerMobileNumber() + " is Already Exists");
			}
			Farmer farmerTemp = new Farmer();
			farmerTemp.setCountry(farmer.getCountry());
			farmerTemp.setState(farmer.getState());
			farmerTemp.setDistrict(farmer.getDistrict());
			farmerTemp.setTaluk(farmer.getTaluk());
			farmerTemp.setVillage(farmer.getVillage());
			farmerTemp.setBrand(farmer.getBrand());
			farmerTemp.setProgramme(farmer.getProgramme());
			farmerTemp.setFarmGroup(farmer.getFarmGroup());
			farmerTemp.setLearnerGroup(farmer.getLearnerGroup());
			farmerTemp.setPuCode(row.getPuCode());
			farmerTemp.setName(row.getFarmerName());
			farmerTemp.setFieldExecutive(row.getFieldExecutiveName());
			farmerTemp.setFarmerCode(row.getFarmerCode());
			farmerTemp.setMobileNumber(row.getFarmerMobileNumber());
			farmersList.add(farmerTemp);
		}
		farmerRepo.saveAll(farmersList);
	}

	@Cacheable(lifetime = 1, unit = TimeUnit.DAYS)
	private AmazonS3 getS3Client () {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(
						new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTH_1)
				.build();
		return s3client;
	}

	@Override
	
	public String saveFileToS3(File file, String folder) {
		
		AmazonS3 s3client =	getS3Client();

		folder = folder + "/";
		s3client.putObject(
				new PutObjectRequest(
						imageBucket,
						folder + file.getName(),
						file)
						.withCannedAcl(CannedAccessControlList.PublicRead));

		URL url = s3client.getUrl(imageBucket, folder + file.getName());
		file.delete();
		return url.toString();

	}

	@Override
	public File getFile(MultipartFile file) {
		try {

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if (StringUtils.isEmpty(fileName))
				throw new FileStorageException("File is Missing");
			if (fileName.contains(".."))
				throw new FileStorageException(
						"Sorry! Filename contains invalid path sequence " + System.nanoTime() + "_" + fileName);
			Path targetLocation = this.root.resolve(System.nanoTime() + "_" + fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return new File(targetLocation.toUri());
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

}
