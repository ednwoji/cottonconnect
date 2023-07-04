package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.FaqDocuments;

@Repository
public interface FaqDocumetsRepository extends JpaRepository<FaqDocuments, Long>{

}
