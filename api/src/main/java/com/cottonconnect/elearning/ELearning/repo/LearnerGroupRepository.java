package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.LearnerGroup;

public interface LearnerGroupRepository extends JpaRepository<LearnerGroup, Long>{
	List<LearnerGroup> findByFarmGroupId(Long farmGroup);
	
	List<LearnerGroup> findByFarmGroupIdIn(List<Long> farmGroup);
	
	List<LearnerGroup> findByIdIn(List<Long> ids);
	
}
