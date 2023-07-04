package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.ProgrammeDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface ProgrammeService {
	ProgrammeDTO saveProgramme(ProgrammeDTO programmeDTO, String userId);
	
	TableResponse getAllPrograms(Integer draw,Integer pageNo, Integer pageSize);
	
	List<ProgrammeDTO> getPrograms();
	
	List<ProgrammeDTO> getProgramByBrand(Long brand);

	ProgrammeDTO getById(Long id);

	void deleteProgram(Long id);

	List<ProgrammeDTO> getProgramByBrands(List<Long> brandId);
}
