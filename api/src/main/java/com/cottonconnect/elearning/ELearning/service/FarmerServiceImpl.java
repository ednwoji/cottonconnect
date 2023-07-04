package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cottonconnect.elearning.ELearning.dto.FarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.LoginDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Farmer;
import com.cottonconnect.elearning.ELearning.model.LearnerGroup;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.model.User;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.DeviceRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.RegisterRepository;
import com.cottonconnect.elearning.ELearning.repo.UserRepository;
import com.cottonconnect.elearning.ELearning.repo.VillageRepository;
import com.cottonconnect.elearning.ELearning.repo.page.FarmerPagedRepository;
import com.utility.Mapper;

@Service
public class FarmerServiceImpl implements FarmerService {

	@Autowired
	FarmerRepository farmerRepsitory;

	@Autowired
	VillageRepository villageRepository;

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	ProgrammeRepository programeRepository;

	@Autowired
	FarmGroupRepository farmGroupRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	FarmerPagedRepository farmerPagedRepository;

	@Autowired
	RegisterRepository registerRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	private LearnerGroupRepository learnerGroupRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public FarmerDTO saveFarmer(FarmerDTO farmerDTO, String userId) {
		Farmer farmer = Mapper.map(farmerDTO, Farmer.class);
		if (!StringUtils.isEmpty(farmerDTO.getId())) {
			Optional<Farmer> farmerOpt = farmerPagedRepository.findById(farmerDTO.getId());
			if (farmerOpt.isPresent()) {
				farmer.setId(farmerOpt.get().getId());
			}
		}
		if (farmerDTO.getProgrammeId() != null) {
			Optional<Programme> programmeOpt = programeRepository.findById(farmerDTO.getProgrammeId());
			if (programmeOpt.isPresent()) {
				farmer.setProgramme(programmeOpt.get());
			}

		}
		if (farmerDTO.getCountryId() != null) {
			Optional<Country> countryOpt = countryRepository.findById(farmerDTO.getCountryId());
			if (countryOpt.isPresent()) {
				farmer.setCountry(countryOpt.get());
			}

		}
		if (farmerDTO.getFarmGroupId() != null) {
			Optional<FarmGroup> farmGroupOpt = farmGroupRepository.findById(farmerDTO.getFarmGroupId());
			if (farmGroupOpt.isPresent()) {
				farmer.setFarmGroup(farmGroupOpt.get());
			}

		}
		if (farmerDTO.getBrandId() != null) {
			Optional<Brand> brandOpt = brandRepository.findById(farmerDTO.getBrandId());
			if (brandOpt.isPresent()) {
				farmer.setBrand(brandOpt.get());
			}
		}
		if (farmerDTO.getLearnerGroupId() != null) {
			Optional<LearnerGroup> learnerGroupOpt = learnerGroupRepository.findById(farmerDTO.getLearnerGroupId());
			if (learnerGroupOpt.isPresent()) {
				farmer.setLearnerGroup(learnerGroupOpt.get());
			}
		}
		Mapper.setAuditable(farmer, userId);
		farmerRepsitory.save(farmer);
		return farmerDTO;
	}

	@Override
	public LoginDTO checkLogin(LoginDTO loginDTO) {
		Farmer farmer = farmerRepsitory.findByMobileNumber(loginDTO.getMobileNumber());
		Register reg = registerRepository.findByMobileNumberAndIsApprovedTrue(loginDTO.getMobileNumber());
		Optional<User> user = userRepository.findByMobileNo(loginDTO.getMobileNumber());
		if (farmer != null) {
			loginDTO.setFarmerName(farmer.getName());
			loginDTO.setFarmerId(farmer.getId());
			return loginDTO;
		} else if (reg != null) {
			loginDTO.setFarmerName(reg.getName());
			loginDTO.setFarmerId(reg.getId());
			return loginDTO;
		} else if (user.isPresent()) {
			loginDTO.setRoleId(user.get().getRole().getId());
			return loginDTO;
		}
		return null;
	}

	@Override
	public TableResponse getAllFarmers(Integer draw, Integer pageNo, Integer pageSize) {

		TableResponse response = null;
		List<List<Object>> famerDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Farmer> farmerPaged = farmerPagedRepository.findAll(paging);
		if (farmerPaged.hasContent()) {
			List<Farmer> farmerList = farmerPaged.getContent();
			famerDtoList = farmerList.stream().map(farmer -> {
				List<Object> countryObjList = new ArrayList<>();
				countryObjList.add(farmer.getFarmerCode());
				countryObjList.add(farmer.getName());
				countryObjList.add(farmer.getProgramme().getName());
				countryObjList.add(farmer.getCountry().getName());
				countryObjList.add(farmer.getFarmGroup().getName());
				countryObjList.add(farmer.getFieldExecutive());
				countryObjList.add(farmer.getCountryCode());
				countryObjList.add(farmer.getMobileNumber());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ farmer.getId() + "')></button>");
				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + farmer.getId()
						+ "')></button>");
				countryObjList.add(sb.toString());
				return countryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, farmerPaged.getTotalElements(), farmerPaged.getTotalElements(),
					famerDtoList);
		} else {
			response = new TableResponse(draw, farmerPaged.getTotalElements(), farmerPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public FarmerDTO findFarmerById(Long id) {
		Optional<Farmer> farmerOpt = farmerPagedRepository.findById(id);
		if (farmerOpt.isPresent()) {
			Farmer farmer = farmerOpt.get();
			return copyToDTO(farmer);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		farmerPagedRepository.deleteById(id);
	}

	@Override
	public FarmerDTO getFarmerByMobile(String mobile) {
		Farmer farmer = farmerRepsitory.findByMobileNumber(mobile);
		if (farmer != null) {
			return copyToDTO(farmer);
		}
		return null;
	}

	private FarmerDTO copyToDTO(Farmer farmer) {
		FarmerDTO farmerDto = new FarmerDTO();
		farmerDto.setId(farmer.getId());
		farmerDto.setCountryCode(farmer.getCountryCode());
		farmerDto.setCountryId(farmer.getCountry().getId());
		farmerDto.setFarmerCode(farmer.getFarmerCode());
		farmerDto.setFarmGroupId(farmer.getFarmGroup().getId());
		farmerDto.setFieldExecutive(farmer.getFieldExecutive());
		farmerDto.setMobileNumber(farmer.getMobileNumber());
		farmerDto.setName(farmer.getName());
		farmerDto.setProgrammeId(farmer.getProgramme().getId());
		farmerDto.setTypez(farmer.getTypez());
		farmerDto.setCountryName(farmer.getCountry().getName());
		farmerDto.setFarmGroupName(farmer.getFarmGroup().getName());
		farmerDto.setProgramme(farmer.getProgramme().getName());
		return farmerDto;
	}

}
