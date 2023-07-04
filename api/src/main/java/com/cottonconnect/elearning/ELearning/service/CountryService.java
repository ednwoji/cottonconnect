package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;

public interface CountryService {
	CountryDTO saveCountry(CountryDTO country) throws CustomException;
	
	CountryDTO findById(Long id);
	
	TableResponse getAllCountries(Integer draw,Integer pageNo, Integer pageSize);
	
	List<CountryDTO> getCountries();

	CountryDTO getById(Long id);

	void delete(Long id);
}
