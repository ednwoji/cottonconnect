package com.cottonconnect.elearning.ELearning.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.KnowledgeCenterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Category;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Farmer;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenterImages;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.model.SubCategory;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CategoryRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCenterRepo;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCenterRepository;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCentreImagesRepo;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.RegisterRepository;
import com.cottonconnect.elearning.ELearning.repo.SubCategoryRepository;

// import sun.misc.BASE64Decoder;

@Service
public class KnowleldgeCenterServiceImpl implements KnowleldgeCenterService {
	private final static Logger LOGGER = Logger.getLogger(KnowleldgeCenterServiceImpl.class.getName());
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private KnowledgeCenterRepo knowledgeCenterRepo;

	@Autowired
	private FarmGroupRepository farmGroupRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ProgrammeRepository programRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private RegisterRepository registerRepository;

	@Value(value = "${image.storage.path}")
	private String filePath;

	@Value(value = "${app.url}")
	private String appUrl;

	@Value(value = "${accessKey}")
	private String accessKey;

	@Value(value = "${secretkey}")
	private String secretkey;

	@Autowired
	UploadService uploadService;

	@Autowired
	private KnowledgeCenterRepository knowledgeCenterRepository;

	@Autowired
	private KnowledgeCentreImagesRepo knowledgeCentreImagesRepo;

	@Autowired
	private LearnerGroupRepository learnerGroupRepository;

	@Override
	public KnowledgeCenterDTO saveKnowledgeCenter(KnowledgeCenterDTO knowledgeCenterDTO, List<File> imageFiles) {
		KnowledgeCenter knowledgeCenter = new KnowledgeCenter();
		if (knowledgeCenterDTO.getId() != null) {
			Optional<KnowledgeCenter> farmerOpt = knowledgeCenterRepo.findById(knowledgeCenterDTO.getId());
			if (farmerOpt.isPresent()) {
				knowledgeCenter.setId(farmerOpt.get().getId());
			}
		}

		if (knowledgeCenterDTO.getLearners() != null) {
			knowledgeCenter.setLearnerGroups(learnerGroupRepository.findByIdIn(knowledgeCenterDTO.getLearners()));
		}

		if (knowledgeCenterDTO.getFarmGroups() != null) {
			knowledgeCenter.setFarmGroups(farmGroupRepository.findByIdIn(knowledgeCenterDTO.getFarmGroups()));
		}

		if (knowledgeCenterDTO.getPrograms() != null) {
			knowledgeCenter.setProgrammes(programRepository.findByIdIn(knowledgeCenterDTO.getPrograms()));
		}

		if (knowledgeCenterDTO.getCountries() != null) {
			List<Country> countryList = countryRepository.findByIdIn(knowledgeCenterDTO.getCountries());
			if (countryList != null) {
				knowledgeCenter.setCountries(countryList);
			}
		}

		if (knowledgeCenterDTO.getBrands() != null) {
			List<Brand> brandList = brandRepository.findByIdIn(knowledgeCenterDTO.getBrands());
			if (brandList != null) {
				knowledgeCenter.setBrands(brandList);
			}
		}

		knowledgeCenter.setType(knowledgeCenterDTO.getType());
		knowledgeCenter.setIdentification(knowledgeCenterDTO.getIdentification());
		knowledgeCenter.setName(knowledgeCenterDTO.getName());
		knowledgeCenter.setTitle(knowledgeCenterDTO.getTitle());
		knowledgeCenter.setNotes(knowledgeCenterDTO.getNotes());
		knowledgeCenter.setDescription(knowledgeCenterDTO.getDescription());
		knowledgeCenter.setTypez(knowledgeCenterDTO.getTypez());
		knowledgeCenter.setExternalLink(knowledgeCenterDTO.getExternalLink());
		SubCategory subCategory = subCategoryRepository.getOne(Long.valueOf(knowledgeCenterDTO.getSubCategory()));
		knowledgeCenter.setSubCategory(subCategory);
		knowledgeCenter.setActive(true);
		knowledgeCenter.setDeleted(false);
		knowledgeCenter.setCreatedDate(new Date());
		knowledgeCenter.setUpdatedDate(new Date());
		knowledgeCenterRepo.save(knowledgeCenter);
		knowledgeCenterDTO.setId(knowledgeCenter.getId());

		uploadService.save(imageFiles, knowledgeCenter);

		return knowledgeCenterDTO;
	}

	// public static BufferedImage decodeToImage(String imageString) {
	// if (imageString == null || imageString.trim() == "") {
	// return null;
	// }
	// BufferedImage image = null;
	// byte[] imageByte;
	// try {
	// BASE64Decoder decoder = new BASE64Decoder();
	// imageByte = decoder.decodeBuffer(imageString);
	// ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	// image = ImageIO.read(bis);
	// bis.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return image;
	// }

	@Override
	public List<KnowledgeCenterDTO> findAllBySubCategory(Long category) {
		List<KnowledgeCenter> knowledgeList = knowledgeCenterRepo.listKnowledgeCenterByCategory(category);
		List<KnowledgeCenterDTO> knowledgeCenterDTOList = new ArrayList<KnowledgeCenterDTO>();
		if (knowledgeList.size() > 0) {
			knowledgeCenterDTOList = knowledgeList.stream().map(kc -> {
				KnowledgeCenterDTO kDTO = new KnowledgeCenterDTO();
				kDTO.setDescription(kc.getDescription());
				kDTO.setId(kc.getId());
				kDTO.setIdentification(kc.getIdentification());
				kDTO.setName(kc.getName());
				kDTO.setNotes(kc.getNotes());
				kDTO.setTitle(kc.getTitle());
				kDTO.setTypez(kc.getTypez());
				if (kc.getKnowledgeCenterImages().size() > 0) {
					kDTO.setPhotoUrl(kc.getKnowledgeCenterImages().get(0).getImageUrl());
				} else {
					kDTO.setPhotoUrl("https://cotton-connect-images-dev.s3.ap-south-1.amazonaws.com/img/no_image.jpg");
				}
				return kDTO;
			}).collect(Collectors.toList());
		}
		return knowledgeCenterDTOList;
	}

	@Override
	public FileInputStream getPhoto(String fileName) {
		LOGGER.info(filePath + fileName + ".jpg");
		File file = new File(filePath + fileName + ".jpg");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	@Override
	public KnowledgeCenterDTO findById(Long id) {
		Optional<KnowledgeCenter> kcOtp = knowledgeCenterRepo.findById(id);
		if (!kcOtp.isPresent()) {
			return null;
		}
		KnowledgeCenter kc = kcOtp.get();

		String programs = kc.getProgrammes().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
		String farmGroups = kc.getFarmGroups().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
		String learners = kc.getLearnerGroups().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));

		KnowledgeCenterDTO kDTO = new KnowledgeCenterDTO();
		kDTO.setDescription(kc.getDescription());
		kDTO.setId(kc.getId());
		kDTO.setIdentification(kc.getIdentification());
		kDTO.setName(kc.getName());
		kDTO.setNotes(kc.getNotes());
		kDTO.setTitle(kc.getTitle());
		kDTO.setTypez(kc.getTypez());
		kDTO.setExternalLink(kc.getExternalLink());

		List<Long> learnerList = kc.getLearnerGroups().stream().map(lg -> lg.getId()).collect(Collectors.toList());
		List<Long> farmGroupList = kc.getFarmGroups().stream().map(farmGroup -> farmGroup.getId())
				.collect(Collectors.toList());
		List<Long> programList = kc.getProgrammes().stream().map(program -> program.getId())
				.collect(Collectors.toList());
		List<Long> countriesList = kc.getCountries().stream().map(country -> country.getId())
				.collect(Collectors.toList());
		List<Long> brandList = kc.getBrands().stream().map(brand -> brand.getId()).collect(Collectors.toList());

		List<Long> partnerList = new ArrayList<>();
		for (FarmGroup fg : kc.getFarmGroups()) {
			partnerList.add(fg.getLocalPartner().getId());
		}

		// kDTO.setFarmGroup(kc.getFarmGroup() != null ? kc.getFarmGroup().getId() :
		// 0L);
		// kDTO.setPartner(kc.getFarmGroup().getLocalPartner().getId());
		// kDTO.setProgram(kc.getProgramme().getId());
		kDTO.setBrands(brandList);
		kDTO.setCountries(countriesList);
		kDTO.setPrograms(programList);
		kDTO.setLocalPartners(partnerList);
		kDTO.setFarmGroups(farmGroupList);
		kDTO.setLearners(learnerList);

		kDTO.setImages(kc.getKnowledgeCenterImages().stream().map(kcImg -> {
			KnowledgeCenterImages kcImages = new KnowledgeCenterImages();
			kcImages.setId(kcImg.getId());
			kcImages.setImageUrl(kcImg.getImageUrl());
			return kcImages;
		}).collect(Collectors.toList()));
		String imagesComma = kc.getKnowledgeCenterImages().stream().map(kcImg -> kcImg.getImageUrl())
				.collect(Collectors.joining("##"));

		kDTO.setImagesByComma(imagesComma);
		return kDTO;
	}

	@Override
	public TableResponse getAllRecords(
			Integer draw,
			Integer pageNo,
			Integer pageSize,
			Long catId,
			Long type,
			String search) {
		TableResponse response = null;
		List<List<Object>> kcDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;

		Map<String, String> typeMap = new LinkedHashMap<>();
		typeMap.put("Insects0", "Harmful");
		typeMap.put("Insects1", "Benificial");

		typeMap.put("Manures0", "Conventional");
		typeMap.put("Manures1", "Organic");

		typeMap.put("Fertilizers0", "Conventional");
		typeMap.put("Fertilizers1", "Organic");

		typeMap.put("Nutrients0", "Micro");
		typeMap.put("Nutrients1", "Macro");

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Optional<SubCategory> category = subCategoryRepository.findById(catId);
		Page<KnowledgeCenter> kcPaged = knowledgeCenterRepo.findAllWithPage(
				type,
				catId,
				search.toLowerCase(),
				paging
			);

		if (kcPaged.hasContent()) {
			List<KnowledgeCenter> kcList = kcPaged.getContent();
			kcDtoList = kcList.stream().map(kc -> {
				List<Object> kcObjList = new ArrayList<>();

				String countries = kc.getCountries().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
				String brands = kc.getBrands().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
				String programs = kc.getProgrammes().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
				String farmGroups = kc.getFarmGroups().stream().map(pg -> pg.getName())
						.collect(Collectors.joining(","));

				kcObjList.add("<a href='#' onclick='detail(" + kc.getId()
						+ ")' style='text-decoration:none;color:blue'>" + kc.getName() + "</a>");
				kcObjList.add(countries);
				if (!category.get().getName().equalsIgnoreCase("Diseases")) {
					String name = category.get().getName() + kc.getTypez();
					if (typeMap.containsKey(name)) {
						kcObjList.add(typeMap.get(name));
					} else {
						kcObjList.add("");
					}
				}
				kcObjList.add(brands);
				kcObjList.add(programs.length() > 50 ? programs.substring(0, 45) + "..more" : programs);
				kcObjList.add(farmGroups.length() > 50 ? farmGroups.substring(0, 45) + "..more" : farmGroups);
				kcObjList.add(kc.isActive() ? "Active" : "In Active");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ kc.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + kc.getId()
						+ "')></button>");
				kcObjList.add(sb.toString());
				return kcObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) kcPaged.getTotalElements(), (int) kcPaged.getTotalElements(),
					kcDtoList);
		} else {
			response = new TableResponse(draw, (int) kcPaged.getTotalElements(), (int) kcPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public void saveSubCategory(List<SubCategory> subcategories) {
		subCategoryRepository.saveAll(subcategories);
	}

	@Override
	public void saveCategory(List<Category> categories) {
		categoryRepository.saveAll(categories);
	}

	@Override
	public List<SubCategory> getSubCategoryList() {
		Category cat = new Category();
		cat.setId(1L);
		return subCategoryRepository.findByCategory(cat);
	}

	@Override
	public void delete(Long id) {
		Optional<KnowledgeCenter> kcOtp = knowledgeCenterRepo.findById(id);
		if (!kcOtp.isPresent()) {
			return;
		}
		KnowledgeCenter kc = kcOtp.get();

		knowledgeCentreImagesRepo.deleteAll(kc.getKnowledgeCenterImages());
		knowledgeCenterRepo.deleteById(id);

	}

	@Override
	public void deleteByImageId(Long id) {
		knowledgeCentreImagesRepo.deleteById(id);
	}

	@Override
	public List<KnowledgeCenterDTO> findAllBySubCategory(Long category, String mobile) {
		Farmer farmer = farmerRepository.findByMobileNumber(mobile);
		if (farmer != null) {
			List<Long> farmGroupList = new ArrayList<>();
			farmGroupList.add(farmer.getFarmGroup().getId());
			return knowledgeList(category, farmGroupList);

		} else {
			Register register = registerRepository.findByMobileNumberAndIsApprovedTrue(mobile);
			if (register != null) {
				List<Long> farmGroupList = register.getFarmGroups().stream().map(fg -> fg.getId())
						.collect(Collectors.toList());
				return knowledgeList(category, farmGroupList);
			} else {
				return new ArrayList<>();
			}
		}
	}

	private List<KnowledgeCenterDTO> knowledgeList(Long category, List<Long> farmGroup) {
		List<KnowledgeCenter> knowledgeList = knowledgeCenterRepo.listKnowledgeCenterByCategoryAndFarmGroup(category,
				farmGroup);
		List<KnowledgeCenterDTO> knowledgeCenterDTOList = new ArrayList<KnowledgeCenterDTO>();
		if (knowledgeList.size() > 0) {
			knowledgeCenterDTOList = knowledgeList.stream().map(kc -> {
				KnowledgeCenterDTO kDTO = new KnowledgeCenterDTO();
				kDTO.setDescription(kc.getDescription());
				kDTO.setId(kc.getId());
				kDTO.setIdentification(kc.getIdentification());
				kDTO.setName(kc.getName());
				kDTO.setNotes(kc.getNotes());
				kDTO.setTitle(kc.getTitle());
				kDTO.setTypez(kc.getTypez());
				if (kc.getKnowledgeCenterImages().size() > 0) {
					kDTO.setPhotoUrl(kc.getKnowledgeCenterImages().get(0).getImageUrl());
				} else {
					kDTO.setPhotoUrl("https://cotton-connect-images-dev.s3.ap-south-1.amazonaws.com/img/no_image.jpg");
				}
				return kDTO;
			}).collect(Collectors.toList());
		}
		return knowledgeCenterDTOList;
	}

}
