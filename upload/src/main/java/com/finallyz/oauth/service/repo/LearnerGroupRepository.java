package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finallyz.oauth.service.domain.LearnerGroup;

public interface LearnerGroupRepository extends JpaRepository<LearnerGroup, Long>{
	List<LearnerGroup> findByFarmGroupId(Long farmGroup);
	
	List<LearnerGroup> findByFarmGroupIdIn(List<Long> farmGroup);
	
	List<LearnerGroup> findByIdIn(List<Long> ids);
	
}
