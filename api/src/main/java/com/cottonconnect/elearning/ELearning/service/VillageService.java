package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.VillageDTO;

public interface VillageService {
	VillageDTO saveVillage(VillageDTO talukDto);

	VillageDTO findById(Long id);
	
	TableResponse getAllVillages(Integer draw, Integer pageNo, Integer pageSize,String search);

	VillageDTO getById(Long id);

	List<VillageDTO> getVillageByTaluk(Long talukId);

	void delete(Long id);
}
