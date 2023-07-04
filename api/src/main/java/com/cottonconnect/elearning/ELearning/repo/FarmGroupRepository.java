package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.FarmGroup;

public interface FarmGroupRepository extends JpaRepository<FarmGroup, Long> {
	List<FarmGroup> findByLocalPartnerId(Long partnerId);
	
	List<FarmGroup> findByIdIn(List<Long> ids);
	
	List<FarmGroup> findByLocalPartnerIdIn(List<Long> partners);
	
}
