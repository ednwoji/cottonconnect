package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finallyz.oauth.service.domain.LocalPartnerName;

public interface LocalPartnerRepository extends JpaRepository<LocalPartnerName, Long>{
	@Query(value = "FROM LocalPartnerName lpn JOIN lpn.Programs p on p.id=:programId")
	List<LocalPartnerName> findByProgram(Long programId);
	
	@Query(value = "From LocalPartnerName lpn JOIN lpn.Programs p on p.id in :ids")
	List<LocalPartnerName> findByProgramIdIn(List<Long> ids);
	
}
