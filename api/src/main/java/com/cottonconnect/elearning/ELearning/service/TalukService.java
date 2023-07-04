package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.TalukDTO;

public interface TalukService {
	TalukDTO saveTaluk(TalukDTO talukDto);
	
	TalukDTO findById(Long id);
	
	TableResponse getAllTaluks(Integer draw, Integer pageNo, Integer pageSize);

	List<TalukDTO> getTaluks(Long districtId);

	TalukDTO getById(Long id);

	void delete(Long id);
}
