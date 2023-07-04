package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
	Country findByCode(String code) throws CustomException;
	
	List<Country> findByIdIn(List<Long> id);
	
}
