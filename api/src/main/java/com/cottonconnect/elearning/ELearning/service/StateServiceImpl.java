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

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.StateDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.State;
import com.cottonconnect.elearning.ELearning.repo.StateRepository;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CountryService countryService;

	@Override
	public StateDTO saveState(StateDTO stateDto) {
		State state = new State();
		if (stateDto.getId() != null) {
			Optional<State> stateOpt = stateRepository.findById(stateDto.getId());
			if (stateOpt.isPresent()) {
				state.setId(stateOpt.get().getId());
			}
		}
		state.setCode(stateDto.getCode());
		state.setName(stateDto.getName());
		state.setActive(true);
		CountryDTO countryDto = countryService.findById(stateDto.getCountryId());
		Country country = new Country();
		country.setId(countryDto.getId());
		state.setCountry(country);
		state.setCreatedDate(new Date());
		state.setUpdatedDate(new Date());
		stateRepository.save(state);
		stateDto.setId(state.getId());
		stateDto.setCountryName(countryDto.getName());
		return stateDto;
	}

	@Override
	public StateDTO findById(Long id) {
		Optional<State> stateOptional = stateRepository.findById(id);
		if (stateOptional.isPresent()) {
			State state = stateOptional.get();
			StateDTO stateDto = new StateDTO(state.getId(), state.getCode(), state.getName(),
					state.getCountry().getId(), state.getCountry().getName());
			return stateDto;
		}
		return null;
	}

	@Override
	public TableResponse getAllStates(Integer draw, Integer pageNo, Integer pageSize, String search) {
		TableResponse response = null;
		List<List<Object>> stateDtoList = new ArrayList<List<Object>>();
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<State> statePaged = stateRepository.findAllWithPage(search.toLowerCase(),paging);
		if (statePaged.hasContent()) {
			List<State> stateList = statePaged.getContent();
			stateDtoList = stateList.stream().map(state -> {
				List<Object> stateObjList = new ArrayList<>();
				stateObjList.add(state.getCountry().getName());
				stateObjList.add(state.getCode());
				stateObjList.add(state.getName());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ state.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + state.getId()
						+ "')></button>");
				stateObjList.add(sb.toString());
				return stateObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) statePaged.getTotalElements(), (int) statePaged.getTotalElements(),
					stateDtoList);
		} else {
			response = new TableResponse(draw, statePaged.getTotalElements(), statePaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<StateDTO> getStates(Long countryId) {
		List<StateDTO> stateList = new ArrayList<StateDTO>();

		stateRepository.findByCountry(countryId).forEach(state -> {
			StateDTO countryDto = new StateDTO();
			countryDto.setCode(state.getCode());
			countryDto.setId(state.getId());
			countryDto.setName(state.getName());
			stateList.add(countryDto);
		});
		return stateList;
	}

	@Override
	public StateDTO getById(Long id) {
		Optional<State> stateOpt = stateRepository.findById(id);
		if (stateOpt.isPresent()) {
			State state = stateOpt.get();
			StateDTO stateDTO = new StateDTO();
			stateDTO.setCode(state.getCode());
			stateDTO.setId(state.getId());
			stateDTO.setName(state.getName());
			stateDTO.setCountryId(state.getCountry().getId());
			return stateDTO;
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		stateRepository.deleteById(id);
	}

	@Override
	public List<StateDTO> getStates() {
		List<StateDTO> stateList = new ArrayList<StateDTO>();

		stateRepository.findAll().forEach(state -> {
			StateDTO countryDto = new StateDTO();
			countryDto.setCode(state.getCode());
			countryDto.setId(state.getId());
			countryDto.setName(state.getName());
			stateList.add(countryDto);
		});
		return stateList;
	}
}
