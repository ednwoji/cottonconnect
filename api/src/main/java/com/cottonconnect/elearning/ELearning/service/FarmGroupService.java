package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.FarmGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface FarmGroupService {
	FarmGroupDTO saveFarmGroup(FarmGroupDTO farmGroupDto, String userId);
	
	TableResponse getAllFarmGroups(Integer draw,Integer pageNo, Integer pageSize);
	
	List<FarmGroupDTO> getFarmGroups();

	List<FarmGroupDTO> getFarmGroupByPartner(Long partnerId);

	FarmGroupDTO getById(Long id);

	List<FarmGroupDTO> getByIds(List<Long> partners);

	void deleteBrand(Long id, String userId);

	List<FarmGroupDTO> getFarmGroupByPartners(List<Long> partners);
}
