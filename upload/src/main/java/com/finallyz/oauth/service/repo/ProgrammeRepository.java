package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finallyz.oauth.service.domain.Programme;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long>{
	List<Programme> findByIdIn(List<Long> ids);
}
