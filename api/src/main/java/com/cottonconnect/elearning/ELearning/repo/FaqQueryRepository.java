package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.FaqQuery;

public interface FaqQueryRepository extends JpaRepository<FaqQuery, Long>{
	List<FaqQuery> findByFarmerMobileNumber(String mobile);
	
}
