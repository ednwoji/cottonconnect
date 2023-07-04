package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.StateDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface StateService {
	StateDTO saveState(StateDTO stateDto);

	StateDTO findById(Long id);

	TableResponse getAllStates(Integer draw, Integer pageNo, Integer pageSize,String search);

	List<StateDTO> getStates(Long countryId);

	StateDTO getById(Long id);

	void delete(Long id);

	List<StateDTO> getStates();
}
