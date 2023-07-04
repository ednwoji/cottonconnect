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

import com.cottonconnect.elearning.ELearning.dto.DistrictDTO;
import com.cottonconnect.elearning.ELearning.dto.StateDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.District;
import com.cottonconnect.elearning.ELearning.model.State;
import com.cottonconnect.elearning.ELearning.repo.DistrictRepository;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	StateService stateService;

	@Override
	public DistrictDTO saveDistrict(DistrictDTO districtDto) {
		District district = new District();
		if (districtDto.getId() != null) {
			Optional<District> districtOpt = districtRepository.findById(districtDto.getId());
			if (districtOpt.isPresent()) {
				district.setId(districtOpt.get().getId());
			}
		}
		district.setName(districtDto.getName());
		district.setCode(districtDto.getCode());
		district.setActive(true);
		district.setDeleted(false);
		district.setCreatedDate(new Date());
		district.setUpdatedDate(new Date());

		StateDTO stateDto = stateService.findById(districtDto.getStateId());
		State state = new State();
		state.setId(stateDto.getId());

		district.setState(state);
		districtRepository.save(district);
		districtDto.setId(district.getId());
		districtDto.setStateName(district.getState().getName());
		return districtDto;
	}

	@Override
	public DistrictDTO findById(Long id) {
		Optional<District> districtOptional = districtRepository.findById(id);
		if (districtOptional.isPresent()) {
			District district = districtOptional.get();
			DistrictDTO stateDto = new DistrictDTO(district.getId(), district.getCode(), district.getName(),
					district.getState().getId(), district.getState().getName(),district.getState().getCountry().getId());
			return stateDto;
		}
		return null;

	}

	@Override
	public TableResponse getAllDistricts(Integer draw, Integer pageNo, Integer pageSize,String search) {
		TableResponse response = null;
		List<List<Object>> districtDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<District> districtPaged = districtRepository.findAllWithPage(search.toLowerCase(),paging);
		if (districtPaged.hasContent()) {
			List<District> districtList = districtPaged.getContent();
			districtDtoList = districtList.stream().map(district -> {
				List<Object> districtObjList = new ArrayList<>();
				districtObjList.add(district.getState().getCountry().getName());
				districtObjList.add(district.getState().getName());
				districtObjList.add(district.getCode());
				districtObjList.add(district.getName());

				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ district.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + district.getId()
						+ "')></button>");
				districtObjList.add(sb.toString());

				return districtObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, districtPaged.getTotalElements(), districtPaged.getTotalElements(),
					districtDtoList);
		} else {
			response = new TableResponse(draw, districtPaged.getTotalElements(), districtPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;
	}

	@Override
	public List<DistrictDTO> getDistricts(Long stateId) {
		List<DistrictDTO> districtList = new ArrayList<DistrictDTO>();
		districtRepository.findByState(stateId).forEach(state -> {
			DistrictDTO districtDto = new DistrictDTO();
			districtDto.setCode(state.getCode());
			districtDto.setId(state.getId());
			districtDto.setName(state.getName());
			districtList.add(districtDto);
		});
		return districtList;
	}

	@Override
	public DistrictDTO getById(Long id) {
		Optional<District> districtOpt = districtRepository.findById(id);
		if (districtOpt.isPresent()) {
			District district = districtOpt.get();
			DistrictDTO stateDTO = new DistrictDTO();
			stateDTO.setCode(district.getCode());
			stateDTO.setId(district.getId());
			stateDTO.setName(district.getName());
			stateDTO.setStateId(district.getState().getId());
			stateDTO.setSelectedCountryId(district.getState().getCountry().getId());
			return stateDTO;
		}
		return null;

	}

	@Override
	public void delete(Long id) {
		districtRepository.deleteById(id);
	}

}
