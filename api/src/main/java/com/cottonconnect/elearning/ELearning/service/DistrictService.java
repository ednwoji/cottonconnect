package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.DistrictDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface DistrictService {
	DistrictDTO saveDistrict(DistrictDTO districtDto);
	
	DistrictDTO findById(Long id);
	
	TableResponse getAllDistricts(Integer draw, Integer pageNo, Integer pageSize,String search);

	List<DistrictDTO> getDistricts(Long stateId);

	DistrictDTO getById(Long id);

	void delete(Long id);
}
