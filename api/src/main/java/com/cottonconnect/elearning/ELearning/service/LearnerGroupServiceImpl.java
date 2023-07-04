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

import com.cottonconnect.elearning.ELearning.dto.LearnerGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.LearnerGroup;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.page.LearnerGroupPagedRepository;
import com.utility.Mapper;

@Service
public class LearnerGroupServiceImpl implements LearnerGroupService {

	@Autowired
	private FarmGroupRepository farmGroupRepo;
	@Autowired
	private LearnerGroupRepository learnerGroupRepositoy;
	@Autowired
	private ProgrammeRepository programRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	LearnerGroupPagedRepository learnerGroupPagedRepository;

	@Autowired
	CountryRepository countryRepository;

	@Override
	public LearnerGroupDTO saveLearnerGroup(LearnerGroupDTO learnerGroupDto, String userId) {
		LearnerGroup learnerGroup = Mapper.map(learnerGroupDto, LearnerGroup.class);
		Optional<FarmGroup> farmGroupOpt = farmGroupRepo.findById(learnerGroupDto.getFarmGroupId());
		if (farmGroupOpt.isPresent()) {
			learnerGroup.setFarmGroup(farmGroupOpt.get());
		}
		if (learnerGroupDto.getProgramId() != null) {
			Optional<Programme> programOpt = programRepository.findById(learnerGroupDto.getProgramId());
			if (programOpt.isPresent()) {
				learnerGroup.setProgramme(programOpt.get());
			}
		}

		if (learnerGroupDto.getBrandId() != null) {
			Optional<Brand> brandOpt = brandRepository.findById(learnerGroupDto.getBrandId());
			if (brandOpt.isPresent()) {
				learnerGroup.setBrand(brandOpt.get());
			}
		}

		if (learnerGroupDto.getCountryId() != null) {
			Optional<Country> country = countryRepository.findById(learnerGroupDto.getCountryId());
			if (country.isPresent()) {
				learnerGroup.setCountry(country.get());
			}
		}
		Mapper.setAuditable(learnerGroup, userId);
		learnerGroupRepositoy.save(learnerGroup);
		return learnerGroupDto;
	}

	@Override
	public TableResponse getAllLearners(Integer draw, Integer pageNo, Integer pageSize) {

		TableResponse response = null;
		List<List<Object>> farmGroupDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<LearnerGroup> programPaged = learnerGroupPagedRepository.findAll(paging);
		if (programPaged.hasContent()) {
			List<LearnerGroup> farmGroupList = programPaged.getContent();
			farmGroupDtoList = farmGroupList.stream().map(farmGroup -> {
				List<Object> fgObjList = new ArrayList<>();
				fgObjList.add(farmGroup.getName());
				fgObjList.add(farmGroup.getBrand().getName());
				fgObjList.add(farmGroup.getProgramme().getName());
				fgObjList.add(farmGroup.getFarmGroup().getName());
				fgObjList.add(farmGroup.getNoOfFarmers());
				fgObjList.add(farmGroup.getAcreage());
				fgObjList.add(farmGroup.getEstYield());


				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ farmGroup.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
						+ farmGroup.getId() + "')></button>");
				fgObjList.add(sb.toString());

				return fgObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, programPaged.getTotalElements(), programPaged.getTotalElements(),
					farmGroupDtoList);
		} else {
			response = new TableResponse(draw, programPaged.getTotalElements(), programPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<LearnerGroupDTO> getLearnerGroupByFG(Long fg) {
		List<LearnerGroup> learnerList = learnerGroupRepositoy.findByFarmGroupId(fg);
		List<LearnerGroupDTO> dtoList = learnerList.stream().map(learner -> {
			LearnerGroupDTO dto = new LearnerGroupDTO();
			dto.setId(learner.getId());
			dto.setName(learner.getName());
			return dto;
		}).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<LearnerGroupDTO> getLearnerGroupByFGs(List<Long> fg) {
		List<LearnerGroup> learnerList = learnerGroupRepositoy.findByFarmGroupIdIn(fg);
		List<LearnerGroupDTO> dtoList = learnerList.stream().map(learner -> {
			LearnerGroupDTO dto = new LearnerGroupDTO();
			dto.setId(learner.getId());
			dto.setName(learner.getName());
			return dto;
		}).collect(Collectors.toList());
		return dtoList;

	}

	@Override
	public LearnerGroupDTO getById(Long id) {
		Optional<LearnerGroup> learnerGroupOpt = learnerGroupRepositoy.findById(id);
		LearnerGroupDTO lgDTO = new LearnerGroupDTO();
		if (learnerGroupOpt.isPresent()) {
			LearnerGroup learnerGroup = learnerGroupOpt.get();
			lgDTO.setId(learnerGroup.getId());
			lgDTO.setAcreage(learnerGroup.getAcreage());
			lgDTO.setBrandId(learnerGroup.getBrand().getId());
			lgDTO.setEstYield(learnerGroup.getEstYield());
			lgDTO.setFarmGroupId(learnerGroup.getFarmGroup().getId());
			lgDTO.setLatitude(learnerGroup.getLatitude());
			lgDTO.setLongitude(learnerGroup.getLongitude());
			lgDTO.setName(learnerGroup.getName());
			lgDTO.setNoOfFarmers(learnerGroup.getNoOfFarmers());
			lgDTO.setPartnerId(learnerGroup.getFarmGroup().getLocalPartner().getId());
			lgDTO.setProgramId(learnerGroup.getProgramme().getId());
			lgDTO.setCountryId(learnerGroup.getCountry() != null ? learnerGroup.getCountry().getId() : 0L);
		}
		return lgDTO;
	}

}
