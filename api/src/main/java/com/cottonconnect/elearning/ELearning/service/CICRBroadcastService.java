package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.CICRBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface CICRBroadcastService {

	Void saveCICR(
			CICRBroadcastDTO districtDto,
			String videoUrl,
			String audioUrl,
			String documentUrl);

	TableResponse getAllRecords();

	void delete(Long id);

}
