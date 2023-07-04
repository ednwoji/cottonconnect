package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.VillageDTO;

import java.util.List;

public interface VillageService {
	VillageDTO saveVillage(VillageDTO talukDto);

	VillageDTO findById(Long id);
	
	TableResponse getAllVillages(Integer draw, Integer pageNo, Integer pageSize);

	VillageDTO getById(Long id);

	void delete(Long id);

	List<VillageDTO> getVillageByTaluk(Long talukId);
}
