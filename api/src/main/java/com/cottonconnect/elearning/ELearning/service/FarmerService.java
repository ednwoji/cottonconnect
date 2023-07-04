package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.FarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.LoginDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface FarmerService {
	FarmerDTO saveFarmer(FarmerDTO farmer,String userId);
	
	LoginDTO checkLogin(LoginDTO loginDTO);

	TableResponse getAllFarmers(Integer draw, Integer start, Integer length);

	FarmerDTO findFarmerById(Long id);

	void delete(Long id);

	FarmerDTO getFarmerByMobile(String mobile);
}
