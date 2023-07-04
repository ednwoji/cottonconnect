package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finallyz.oauth.service.domain.FarmGroup;

public interface FarmGroupRepository extends JpaRepository<FarmGroup, Long> {
	List<FarmGroup> findByLocalPartnerId(Long partnerId);
	
	List<FarmGroup> findByIdIn(List<Long> ids);
	
	List<FarmGroup> findByLocalPartnerIdIn(List<Long> partners);
	
}
