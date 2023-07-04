package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.LocalPartnerNameDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;

public interface LocalPartnerService {
	LocalPartnerNameDTO saveLocalPartner(LocalPartnerNameDTO LocalPartnerName, String userId);

	TableResponse getAllPartners(Integer draw, Integer start, Integer length,String search);
	
	List<LocalPartnerNameDTO> getLocalPartner(Long programId);

	LocalPartnerNameDTO getById(Long id);
	
	List<LocalPartnerNameDTO> getLocalPartners(List<Long> programId);

	void delete(Long id);
}
