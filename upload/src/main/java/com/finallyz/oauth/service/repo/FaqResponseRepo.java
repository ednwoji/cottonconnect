package com.finallyz.oauth.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finallyz.oauth.service.domain.FaqQueryResponse;

@Repository
public interface FaqResponseRepo extends JpaRepository<FaqQueryResponse, Long>{

}
