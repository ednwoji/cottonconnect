package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.finallyz.oauth.service.domain.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
	Country findByCode(String code) throws CustomException;
	
	List<Country> findByIdIn(List<Long> id);
	
}
