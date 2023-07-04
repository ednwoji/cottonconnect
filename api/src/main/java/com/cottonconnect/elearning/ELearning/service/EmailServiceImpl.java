package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cottonconnect.elearning.ELearning.model.*;
import com.cottonconnect.elearning.ELearning.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cottonconnect.elearning.ELearning.dto.EmailDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.repo.page.EmailPagedRepository;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ProgrammeRepository programmeRepository;
	@Autowired
	private FarmGroupRepository farmGroupRepository;
	@Autowired
	private LearnerGroupRepository learnerGroupRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private EmailPagedRepository emailPagedRepository;
	@Autowired
	private EmailListRepository emailListRepository;

	@Autowired
	private EmailListNewRepository emailListNewRepository;

	@Override
	public Email save(EmailDTO emailDTO) {
		Email email = new Email();
		Email_list_new emailNew = new Email_list_new();

		if (emailDTO.getId() != null) {
			log.info("I am here now");
			email.setId(emailDTO.getId());
//			List<EmailList> emailList = emailListRepository.findByEmailId(emailDTO.getId());
//			if (emailList != null) {
//				log.info("deleting now");
//				emailListRepository.deleteAll(emailList);
//			}
		}

		if (emailDTO.getCountry() != null) {
			log.info("Checking country:::::::");
			Optional<Country> countryOpt = countryRepository.findById(emailDTO.getCountry());
			if (countryOpt.isPresent()) {
				email.setCountry(countryOpt.get());
			}
		}

		if (emailDTO.getBrand() != null) {
			log.info("Checking Brand:::::::");

			Optional<Brand> brandOpt = brandRepository.findById(emailDTO.getBrand());
			if (brandOpt.isPresent()) {
				email.setBrand(brandOpt.get());
			}
		}

		if (emailDTO.getLearners() != null) {
			log.info("Checking Learners:::::::");

			email.setLearnerGroups(learnerGroupRepository.findByIdIn(emailDTO.getLearners()));
		}

		if (emailDTO.getPrograms() != null) {
			log.info("Checking Programs:::::::");
			List<Programme> programList = programmeRepository.findByIdIn(emailDTO.getPrograms());
			email.setProgrammes(programList);
		}

		if (emailDTO.getFarmGroups() != null) {
			log.info("Checking Farm Groups:::::::");
			List<FarmGroup> farmGroupList = farmGroupRepository.findByIdIn(emailDTO.getFarmGroups());
			email.setFarmGroups(farmGroupList);
		}

		if (emailDTO.getLearners() != null) {
			log.info("Checking Learners:::::::");
			List<LearnerGroup> learnerGroupList = learnerGroupRepository.findByIdIn(emailDTO.getLearners());
			email.setLearnerGroups(learnerGroupList);
		}

		if (emailDTO.getEmails() != null) {
			log.info("Getting Email List:::::::");

			List<EmailList> emailList = emailDTO.getEmails().stream().filter(e1 -> !StringUtils.isEmpty(e1)).map(el -> {
				EmailList eList = new EmailList();
				eList.setEmail(el);
				eList.setEmailId(email);
				return eList;
			}).collect(Collectors.toList());

			email.setEmailLists(emailList);
		}

		Email savedEmail = emailRepository.save(email);

		emailNew.setEmail_id(savedEmail.getId());

		List<String> emailList = emailDTO.getEmails();
		String emailString = String.join(",", emailList);
		emailNew.setEmailAddress(emailString);



		emailListNewRepository.save(emailNew);
		email.setEmailings(emailString);

//		emailListRepository.saveAll(email.getEmailLists());

		return email;
	}

	@Override
	public TableResponse getEmails(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> emailDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);

		Page<Email> countryPaged = emailPagedRepository.findByIsDeletedFalse(paging);

		if (countryPaged.hasContent()) {
			List<Email> emailList = countryPaged.getContent();

			emailDtoList = emailList.stream().map(email -> {
				String emails = email.getEmailLists().stream().map(pg -> pg.getEmail())
						.collect(Collectors.joining(","));
				String programs = email.getProgrammes().stream().map(pg -> pg.getName())
						.collect(Collectors.joining(","));
				String farmGroups = email.getFarmGroups().stream().map(pg -> pg.getName())
						.collect(Collectors.joining(","));
				List<Object> kcObjList = new ArrayList<>();
				String emailDisp = emails.length() > 50 ? emails.substring(0, 45) + "..more" : emails;
				kcObjList.add(emailDisp);
				kcObjList.add(email.getCountry().getName());
				kcObjList.add(email.getBrand() != null ?email.getBrand().getName():"");
				kcObjList.add(programs.length() > 50 ? programs.substring(0, 45) + "..more" : programs);
				kcObjList.add(farmGroups.length() > 50 ? farmGroups.substring(0, 45) + "..more" : farmGroups);

				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ email.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + email.getId()
						+ "')></button>");
				kcObjList.add(sb.toString());

				return kcObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) countryPaged.getTotalElements(),
					(int) countryPaged.getTotalElements(), emailDtoList);
		} else {
			response = new TableResponse(draw, (int) countryPaged.getTotalElements(),
					(int) countryPaged.getTotalElements(), new ArrayList<>());
		}
		return response;
	}

	@Override
	public EmailDTO findById(Long id) {
		Optional<Email> emailOpt = emailRepository.findById(id);
		EmailDTO emailDto = new EmailDTO();
		if (emailOpt.isPresent()) {
			Email email = emailOpt.get();
			List<Long> learnerList = email.getLearnerGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> farmGroupList = email.getFarmGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> programList = email.getProgrammes().stream().map(lg -> lg.getId()).collect(Collectors.toList());
			List<Long> partnerList = new ArrayList<>();
			for (FarmGroup fg : email.getFarmGroups()) {
				partnerList.add(fg.getLocalPartner().getId());
			}

			emailDto.setId(email.getId());
			emailDto.setCountry(email.getCountry().getId());
			emailDto.setBrand(email.getBrand()!=null?email.getBrand().getId():0L);
			emailDto.setPrograms(programList);
			emailDto.setLocalPartners(partnerList);
			emailDto.setFarmGroups(farmGroupList);
			emailDto.setLearners(learnerList);
			emailDto.setEmails(email.getEmailLists().stream().map(eL -> eL.getEmail()).collect(Collectors.toList()));
		}
		return emailDto;
	}

	@Override
	public void delete(Long id, String user) {
		Optional<Email> emailOpt = emailRepository.findById(id);
		if (emailOpt.isPresent()) {
			Email email = emailOpt.get();
			email.setDeleted(true);
			emailRepository.save(email);
		}
	}

}
