package com.cottonconnect.elearning.ELearning.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.NotificationDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Farmer;
import com.cottonconnect.elearning.ELearning.model.Notification;
import com.cottonconnect.elearning.ELearning.model.User;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.NotificationRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.UserRepository;
import com.cottonconnect.elearning.ELearning.repo.page.NotificationPagedRepository;
import com.utility.Mapper;

@Service
public class NotificationService {

	@Autowired
	private FarmGroupRepository farmGroupRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ProgrammeRepository programRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private NotificationPagedRepository notificationPagedRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private LearnerGroupRepository learnerGroupRepository;

	@Autowired
	private UserRepository userRepository;

	public void save(NotificationDTO notificationDTO, HttpServletResponse res) throws IOException {
		Notification notification = new Notification();

		if (notificationDTO.getLearners() != null) {
			notification.setLearnerGroups(learnerGroupRepository.findByIdIn(notificationDTO.getLearners()));
		}

		if (notificationDTO.getFarmGroups() != null) {
			notification.setFarmGroups(farmGroupRepository.findByIdIn(notificationDTO.getFarmGroups()));
		}

		if (notificationDTO.getPrograms() != null) {
			notification.setProgrammes(programRepository.findByIdIn(notificationDTO.getPrograms()));
		}

		if (notificationDTO.getCountries() != null) {
			List<Country> countryList = countryRepository.findByIdIn(notificationDTO.getCountries());
			if (countryList != null) {
				notification.setCountries(countryList);
			}
		}

		if (notificationDTO.getBrands() != null) {
			List<Brand> brandList = brandRepository.findByIdIn(notificationDTO.getBrands());
			if (brandList != null) {
				notification.setBrands(brandList);
			}
		}

		if (notificationDTO.getUsers() != null) {
			List<User> userList = userRepository.findByIdIn(notificationDTO.getUsers());
			if (userList != null) {
				notification.setUsers(userList);
			}
		}

		notification.setActive(true);
		notification.setMsg(notificationDTO.getMsg());
		notification.setDescription(notificationDTO.getDescription());
		Mapper.setAuditable(notification, "admin");
		notificationPagedRepository.save(notification);
	}

	public TableResponse getAllRecords(Integer draw, Integer pageNo, Integer pageSize, Long catId,String search) {

		TableResponse response = null;
		List<List<Object>> notificationDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Notification> notificationPaged = notificationPagedRepository.findAllWithPage(search.toLowerCase() ,paging);
		if (notificationPaged.hasContent()) {
			List<Notification> notificationList = notificationPaged.getContent();
			notificationDtoList = notificationList.stream().map(notification -> {
				List<Object> notificationObjList = new ArrayList<>();
				notificationObjList.add(notification.getMsg());
				//notificationObjList.add(notification.getFarmGroup() != null ? notification.getFarmGroup().getName() : "");
				notificationObjList.add(notification.getCreatedUser());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ notification.getId() + "')></button>");
				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
						+ notification.getId() + "')></button>");
				notificationObjList.add(sb.toString());
				return notificationObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) notificationPaged.getTotalElements(),
					(int) notificationPaged.getTotalElements(), notificationDtoList);
		} else {
			response = new TableResponse(draw, (int) notificationPaged.getTotalElements(),
					(int) notificationPaged.getTotalElements(), new ArrayList<>());
		}
		return response;

	}

	public List<NotificationDTO> getNotifications(String mobileNumber) {
		Farmer farmer = farmerRepository.findByMobileNumber(mobileNumber);
		if (farmer != null) {
			List<Notification> notifications = notificationRepository
					.listNotificationByFamGroup(farmer.getFarmGroup().getId());
			DateFormat df = new SimpleDateFormat("YYYY/MMM/dd");

			List<NotificationDTO> list = notifications.stream().map(notification -> {
				NotificationDTO dto = new NotificationDTO();
				dto.setId(notification.getId());
				dto.setDescription(notification.getDescription());
				dto.setMsg(notification.getMsg());
				dto.setCreatedDate(df.format(notification.getCreatedDate()));
				return dto;
			}).collect(Collectors.toList());

			return list;
		}
		return new ArrayList<>();
	}

	public void delete(Long id) {
		notificationRepository.deleteById(id);
	}

	public NotificationDTO findById(Long id) {
		Optional<Notification> notificationOpt = notificationRepository.findById(id);
		if (notificationOpt.isPresent()) {
			Notification notification = notificationOpt.get();

			List<Long> learnerList = notification.getLearnerGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> farmGroupList = notification.getFarmGroups().stream().map(farmGroup -> farmGroup.getId())
					.collect(Collectors.toList());
			List<Long> programList = notification.getProgrammes().stream().map(program -> program.getId())
					.collect(Collectors.toList());
			List<Long> countriesList = notification.getCountries().stream().map(country -> country.getId())
					.collect(Collectors.toList());
			List<Long> brandList = notification.getBrands().stream().map(brand -> brand.getId())
					.collect(Collectors.toList());

			List<Long> partnerList = new ArrayList<>();
			for (FarmGroup fg : notification.getFarmGroups()) {
				partnerList.add(fg.getLocalPartner().getId());
			}

			NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setId(notification.getId());
			notificationDTO.setCountries(countriesList);
			notificationDTO.setBrands(brandList);
			notificationDTO.setPrograms(programList);
			notificationDTO.setLocalPartners(partnerList);
			notificationDTO.setFarmGroups(farmGroupList);
			notificationDTO.setLearners(learnerList);
			notificationDTO.setMsg(notification.getMsg());
			notificationDTO.setDescription(notification.getDescription());
			// notificationDTO.setL
			return notificationDTO;
		}
		return null;
	}
}
