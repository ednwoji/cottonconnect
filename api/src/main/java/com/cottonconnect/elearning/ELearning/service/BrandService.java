package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.BrandDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface BrandService {
	BrandDTO saveBrand(BrandDTO brandDto, String userId);

	TableResponse getAllBrands(Integer draw, Integer start, Integer length);
	
	List<BrandDTO> getBrands();

	BrandDTO getById(Long id);

	void deleteBrand(Long id);

	List<BrandDTO> getByCountryId(Long id);

	List<BrandDTO> getByCountryIds(List<Long> id);
}
