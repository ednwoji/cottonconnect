package com.finallyz.oauth.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finallyz.oauth.service.domain.FaqDocuments;

@Repository
public interface FaqDocumetsRepository extends JpaRepository<FaqDocuments, Long>{

}
