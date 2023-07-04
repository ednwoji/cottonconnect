package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.FarmGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.LocalPartnerName;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.LocalPartnerRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.page.FarmGroupPagedRepository;
import com.utility.Mapper;

@Service
public class FarmGroupServiceImpl implements FarmGroupService {
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private LocalPartnerRepository localPartnerRepository;
	@Autowired
	private FarmGroupRepository farmGroupRespository;
	@Autowired
	private FarmGroupPagedRepository farmGroupPagedRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ProgrammeRepository programmeRepository;

	@Override
	public FarmGroupDTO saveFarmGroup(FarmGroupDTO farmGroupDto, String userId) {
		FarmGroup farmGroup = Mapper.map(farmGroupDto, FarmGroup.class);
		if (farmGroupDto.getId() != null) {
			farmGroup.setId(farmGroupDto.getId());
		}
		Optional<Country> countryOpt = countryRepository.findById(farmGroupDto.getCountryId());
		if (countryOpt.isPresent()) {
			farmGroup.setCountry(countryOpt.get());
		}
		Optional<LocalPartnerName> localPartnerOpt = localPartnerRepository.findById(farmGroupDto.getPartnerId());
		if (localPartnerOpt.isPresent()) {
			farmGroup.setLocalPartner(localPartnerOpt.get());
		}
		if (farmGroupDto.getBrandId() != null) {
			Optional<Brand> brand = brandRepository.findById(farmGroupDto.getBrandId());
			if (brand.isPresent()) {
				farmGroup.setBrand(brand.get());
			}
		}
		if (farmGroupDto.getProgramId() != null) {
			Optional<Programme> program = programmeRepository.findById(farmGroupDto.getProgramId());
			if (program.isPresent()) {
				farmGroup.setProgram(program.get());
			}
		}

		Mapper.setAuditable(farmGroup, userId);
		farmGroupRespository.save(farmGroup);
		return farmGroupDto;
	}

	@Override
	public TableResponse getAllFarmGroups(Integer draw, Integer pageNo, Integer pageSize) {
		String[] types = { "", "Farm Group", "PU", "Organic Farm Group" };
		TableResponse response = null;
		List<List<Object>> farmGroupDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<FarmGroup> programPaged = farmGroupPagedRepository.findByIsDeletedFalse(paging);
		if (programPaged.hasContent()) {
			List<FarmGroup> farmGroupList = programPaged.getContent();
			farmGroupDtoList = farmGroupList.stream().map(farmGroup -> {
				List<Object> fgObjList = new ArrayList<>();
				fgObjList.add(farmGroup.getName());
				fgObjList.add(
						farmGroup.getTypez() != null ? types[Integer.parseInt(farmGroup.getTypez().toString())] : "");
				fgObjList.add(farmGroup.getCountry().getName());
				fgObjList.add(farmGroup.getLocalPartner().getName());
				fgObjList.add(farmGroup.getNoOfFarmers());
				fgObjList.add(farmGroup.getAcreage());
				fgObjList.add(farmGroup.getEstYield());
				fgObjList.add(farmGroup.getLatitude());
				fgObjList.add(farmGroup.getLongitude());
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
	public List<FarmGroupDTO> getFarmGroups() {
		List<FarmGroup> farmGroupList = farmGroupRespository.findAll();
		List<FarmGroupDTO> farmGroupDTO = farmGroupList.stream().map(fg -> {
			FarmGroupDTO fdDto = new FarmGroupDTO();
			fdDto.setId(fg.getId());
			fdDto.setName(fg.getName());
			return fdDto;
		}).collect(Collectors.toList());

		return farmGroupDTO;
	}

	@Override
	public List<FarmGroupDTO> getFarmGroupByPartner(Long partnerId) {
		List<FarmGroup> farmGroupList = farmGroupRespository.findByLocalPartnerId(partnerId);
		List<FarmGroupDTO> farmGroupDTOList = farmGroupList.stream().map(farmGroup -> {
			FarmGroupDTO farmGroupDTO = new FarmGroupDTO();
			farmGroupDTO.setId(farmGroup.getId());
			farmGroupDTO.setName(farmGroup.getName());
			return farmGroupDTO;
		}).collect(Collectors.toList());

		return farmGroupDTOList;
	}

	@Override
	public FarmGroupDTO getById(Long id) {
		Optional<FarmGroup> farmGroupOpt = farmGroupRespository.findById(id);
		FarmGroupDTO farmGroupDto = new FarmGroupDTO();
		if (farmGroupOpt.isPresent()) {
			FarmGroup farmGroup = farmGroupOpt.get();
			farmGroupDto = copyToDto(farmGroup);
		}
		return farmGroupDto;
	}

	private FarmGroupDTO copyToDto(FarmGroup farmGroup) {
		FarmGroupDTO farmGroupDto = new FarmGroupDTO();
		farmGroupDto.setId(farmGroup.getId());
		farmGroupDto.setName(farmGroup.getName());
		farmGroupDto.setTypez(farmGroup.getTypez());
		farmGroupDto.setCountryId(farmGroup.getCountry().getId());
		farmGroupDto.setBrandId(farmGroup.getBrand().getId());
		farmGroupDto.setProgramId(farmGroup.getProgram().getId());
		farmGroupDto.setPartnerId(farmGroup.getLocalPartner().getId());
		farmGroupDto.setLatitude(farmGroup.getLatitude());
		farmGroupDto.setLongitude(farmGroup.getLongitude());
		farmGroupDto.setNoOfFarmers(farmGroup.getNoOfFarmers());
		farmGroupDto.setAcreage(farmGroup.getAcreage());
		farmGroupDto.setEstYield(farmGroup.getEstYield());
		return farmGroupDto;
	}

	@Override
	public List<FarmGroupDTO> getByIds(List<Long> partners) {
		List<FarmGroup> farmGroupList = farmGroupRespository.findByIdIn(partners);
		List<FarmGroupDTO> farmGroupDTOList = farmGroupList.stream().map(farmGroup -> {
			FarmGroupDTO farmGroupDTO = new FarmGroupDTO();
			farmGroupDTO.setId(farmGroup.getId());
			farmGroupDTO.setName(farmGroup.getName());
			return farmGroupDTO;
		}).collect(Collectors.toList());

		return farmGroupDTOList;
	}

	@Override
	public void deleteBrand(Long id, String userId) {
		Optional<FarmGroup> fgOpt = farmGroupRespository.findById(id);
		if (fgOpt.isPresent()) {
			farmGroupRespository.deleteById(id);
		}
	}

	@Override
	public List<FarmGroupDTO> getFarmGroupByPartners(List<Long> partners) {

		List<FarmGroup> farmGroupList = farmGroupRespository.findByLocalPartnerIdIn(partners);
		List<FarmGroupDTO> farmGroupDTOList = farmGroupList.stream().map(farmGroup -> {
			FarmGroupDTO farmGroupDTO = new FarmGroupDTO();
			farmGroupDTO.setId(farmGroup.getId());
			farmGroupDTO.setName(farmGroup.getName());
			return farmGroupDTO;
		}).collect(Collectors.toList());

		return farmGroupDTOList;
	
	}

}
