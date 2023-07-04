package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.LearnerGroupDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface LearnerGroupService {
	public LearnerGroupDTO saveLearnerGroup(LearnerGroupDTO learnerGroupDto, String userId);

	public TableResponse getAllLearners(Integer draw, Integer start, Integer length,String search);

	public List<LearnerGroupDTO> getLearnerGroupByFG(Long fg);

	public List<LearnerGroupDTO> getLearnerGroupByFGs(List<Long> fg);

	public LearnerGroupDTO getById(Long id);
}
