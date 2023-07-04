package com.cottonconnect.elearning.ELearning.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.Document;
import com.cottonconnect.elearning.ELearning.dto.EntryDTO;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.dto.FarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Device;
import com.cottonconnect.elearning.ELearning.model.FAQ;
import com.cottonconnect.elearning.ELearning.model.FaqQuery;
import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;
import com.cottonconnect.elearning.ELearning.repo.DeviceRepository;
import com.cottonconnect.elearning.ELearning.repo.FAQRepository;
import com.cottonconnect.elearning.ELearning.repo.FaqQueryRepository;
import com.cottonconnect.elearning.ELearning.repo.FaqResponseRepo;
import com.cottonconnect.elearning.ELearning.repo.page.FAQPagedRepository;
import com.cottonconnect.elearning.ELearning.repo.page.FaqQueryPagedRepository;
import com.utility.Mapper;

@Service
public class FAQServiceImpl implements FAQService {
	@Autowired
	FAQRepository faqReposiotry;

	@Autowired
	FAQPagedRepository faqQuestionPagedRepo;

	@Autowired
	FaqQueryRepository faqQueryRepository;

	@Autowired
	UploadService uploadService;

	@Autowired
	FaqQueryPagedRepository faqPagedRepo;

	@Autowired
	FaqResponseRepo faqResponseRepo;

	@Autowired
	DeviceRepository deviceRepo;

	@Override
	public FAQ saveFaq(FAQ faqList) {
		faqReposiotry.save(faqList);
		return faqList;
	}

	@Override
	public FaqQueryDTO saveFaqQuery(FaqQueryDTO faqQueryDTO) {
		FaqQuery faq = new FaqQuery();
		faq.setQuery(faqQueryDTO.getName());

		Device device = deviceRepo.findByImei(faqQueryDTO.getImei());
		if (device != null) {
			faq.setFarmer(device.getFarmer());
			faq.setImei(faqQueryDTO.getImei());
			Mapper.setAuditable(faq, faq.getFarmer().getName());
			faqQueryRepository.save(faq);
			uploadService.saveDocumet(faqQueryDTO, faq);
		}

		return faqQueryDTO;
	}

	@Override
	public TableResponse getAllFaqQueries(Integer draw, Integer pageNo, Integer pageSize,String search) {
		DateFormat df = new SimpleDateFormat("YYYY/MMM/dd");
		TableResponse response = null;
		List<List<Object>> countryDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<FaqQuery> faqQueryPaged = faqPagedRepo.findAllWithPage(search.toLowerCase(),paging);
		if (faqQueryPaged.hasContent()) {
			List<FaqQuery> faqQueryList = faqQueryPaged.getContent();
			countryDtoList = faqQueryList.stream().map(faqQuery -> {
				List<Object> faqQueryObjList = new ArrayList<>();
				faqQueryObjList.add(df.format(faqQuery.getCreatedDate()));
				faqQueryObjList.add("<a href='#' style='color:blue' onclick='detail(" + faqQuery.getId() + ")'>"
						+ faqQuery.getFarmer().getName() + "</a>");
				faqQueryObjList.add(faqQuery.getFarmer().getFarmGroup().getName());
				faqQueryObjList.add(faqQuery.getFarmer().getCountry().getName());
				Calendar c = Calendar.getInstance();
				c.setTime(faqQuery.getCreatedDate());
				c.add(Calendar.DAY_OF_MONTH, 7);
				String newDate = df.format(c.getTime());

				if (c.getTime().after(new Date())) {
					faqQueryObjList.add(newDate);
					faqQueryObjList.add("No");
					faqQueryObjList
							.add(faqQuery.getResponseList() != null && faqQuery.getResponseList().size()>0 ? faqQuery.getResponseList().get(0).getCreatedUser()
									: "");
				} else {
					faqQueryObjList.add("<b style='color:red'>" + newDate + "</b>");
					faqQueryObjList.add("Yes");
					faqQueryObjList.add("");
				}
				if (faqQuery.getResponseList() == null || faqQuery.getResponseList().isEmpty()) {
					faqQueryObjList.add("<b style='color:red'>Pending</b>");
				} else {
					faqQueryObjList.add("<b style='color:green'>Resolved</b>");
				}

				return faqQueryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, faqQueryPaged.getTotalElements(), faqQueryPaged.getTotalElements(),
					countryDtoList);
		} else {
			response = new TableResponse(draw, faqQueryPaged.getTotalElements(), faqQueryPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;
	}

	@Override
	public FaqQueryDTO findById(Long id) {
		DateFormat df = new SimpleDateFormat("YYYY/MMM/dd");
		Optional<FaqQuery> faqQueryOpt = faqQueryRepository.findById(id);
		FaqQueryDTO faqQuery = new FaqQueryDTO();
		if (faqQueryOpt.isPresent()) {
			FaqQuery query = faqQueryOpt.get();
			faqQuery.setId(query.getId());
			FarmerDTO farmer = new FarmerDTO();
			farmer.setId(query.getFarmer().getId());
			farmer.setName(query.getFarmer().getName());
			farmer.setMobileNumber(query.getFarmer().getMobileNumber());
			farmer.setCountryName(query.getFarmer().getCountry().getName());
			farmer.setFarmGroupName(query.getFarmer().getFarmGroup().getName());

			faqQuery.setFarmer(farmer);
			faqQuery.setName(query.getQuery());
			faqQuery.setCreatedDate(df.format(query.getCreatedDate()));
			Device device = deviceRepo.findByImei(query.getImei());
			if (device != null) {
				faqQuery.setImei(device.getImei());
				faqQuery.setDevie(device);
			}

			List<Document> documentList = query.getFaqImages().stream().map(faqImage -> {
				Document doc = new Document();
				doc.setUrl(faqImage.getUrl());
				doc.setDocType(faqImage.getDocType());
				return doc;
			}).collect(Collectors.toList());

			List<FaqQueryResponse> responseList = query.getResponseList();

			faqQuery.setFaqResponse(responseList);
			faqQuery.setDocuments(documentList);
		}
		return faqQuery;
	}

	@Override
	public TableResponse getAllQuestion(Integer draw, Integer pageNo, Integer pageSize,String search) {

		DateFormat df = new SimpleDateFormat("YYYY/MMM/dd");
		TableResponse response = null;
		List<List<Object>> countryDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<FAQ> faqQueryPaged = faqQuestionPagedRepo.findAllWithPage(search.toLowerCase(),paging);
		if (faqQueryPaged.hasContent()) {
			List<FAQ> faqQueryList = faqQueryPaged.getContent();
			countryDtoList = faqQueryList.stream().map(faqQuery -> {
				List<Object> faqQueryObjList = new ArrayList<>();
				faqQueryObjList.add(faqQuery.getQuestion() != null
						? faqQuery.getQuestion().length() > 50 ? faqQuery.getQuestion().substring(0, 50) + "..more"
								: faqQuery.getQuestion()
						: "");
				faqQueryObjList.add(
						faqQuery.getAnswer() != null
								? faqQuery.getAnswer().length() > 50 ? faqQuery.getAnswer().substring(0, 50) + "..more"
										: faqQuery.getAnswer()
								: "");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ faqQuery.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + faqQuery.getId()
						+ "')></button>");
				faqQueryObjList.add(sb.toString());
				return faqQueryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, faqQueryPaged.getTotalElements(), faqQueryPaged.getTotalElements(),
					countryDtoList);
		} else {
			response = new TableResponse(draw, faqQueryPaged.getTotalElements(), faqQueryPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<EntryDTO> getFaqEntries() {
		List<FAQ> faqList = faqReposiotry.findAll();
		List<EntryDTO> entriesList = new ArrayList<EntryDTO>();
		entriesList = faqList.stream().map(faq -> {
			EntryDTO entry = new EntryDTO();
			entry.setTitle(faq.getQuestion());

			return entry;
		}).collect(Collectors.toList());
		return entriesList;
	}

	@Override
	public List<EntryDTO> getFaqQuestionList() {
		List<FAQ> faqList = faqReposiotry.findAll();

		List<EntryDTO> entryList = faqList.stream().map(faq -> {
			EntryDTO entry = new EntryDTO();
			entry.setTitle(faq.getQuestion());
			List<EntryDTO> entryChildren = new ArrayList<>();
			EntryDTO children = new EntryDTO();
			children.setTitle(faq.getAnswer());
			entryChildren.add(children);
			entry.setChildren(entryChildren);
			return entry;
		}).collect(Collectors.toList());

		return entryList;
	}

	@Override
	public FAQ faqById(Long id) {
		Optional<FAQ> faqOpt = faqReposiotry.findById(id);
		if (faqOpt.isPresent()) {
			return faqOpt.get();
		}
		return null;
	}

	@Override
	public void deleteQueryResponse(Long id) {
		faqResponseRepo.deleteById(id);
	}

	@Override
	public List<FaqQueryDTO> getByFarmer(String farmerId) {
		List<FaqQuery> queryList = faqQueryRepository.findByFarmerMobileNumber(farmerId);
		List<FaqQueryDTO> faqList = queryList.stream().map(faq -> {
			return copyToDTO(faq);
		}).collect(Collectors.toList());

		return faqList;
	}

	FaqQueryDTO copyToDTO(FaqQuery query) {
		FaqQueryDTO faqQuery = new FaqQueryDTO();
		DateFormat df = new SimpleDateFormat("YYYY/MMM/dd");
		faqQuery.setId(query.getId());
		FarmerDTO farmer = new FarmerDTO();
		farmer.setId(query.getFarmer().getId());
		farmer.setName(query.getFarmer().getName());
		farmer.setMobileNumber(query.getFarmer().getMobileNumber());
		farmer.setCountryName(query.getFarmer().getCountry().getName());
		farmer.setFarmGroupName(query.getFarmer().getFarmGroup().getName());

		faqQuery.setFarmer(farmer);
		faqQuery.setName(query.getQuery());
		faqQuery.setCreatedDate(df.format(query.getCreatedDate()));
		Device device = deviceRepo.findByImei(query.getImei());
		if (device != null) {
			faqQuery.setImei(device.getImei());
			faqQuery.setDevie(device);
		}

		List<Document> documentList = query.getFaqImages().stream().map(faqImage -> {
			Document doc = new Document();
			doc.setUrl(faqImage.getUrl());
			doc.setDocType(faqImage.getDocType());
			return doc;
		}).collect(Collectors.toList());

		List<FaqQueryResponse> responseList = query.getResponseList();

		faqQuery.setFaqResponse(responseList);
		faqQuery.setDocuments(documentList);

		return faqQuery;
	}
}
