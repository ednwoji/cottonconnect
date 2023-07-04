package com.finallyz.oauth.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finallyz.oauth.service.domain.FaqQuery;

public interface FaqQueryRepository extends JpaRepository<FaqQuery, Long>{
	
}
