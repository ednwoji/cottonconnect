package com.cottonconnect.elearning.ELearning.service;

import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.EntryDTO;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.FAQ;

public interface FAQService {
	FAQ saveFaq(FAQ faqList);

	FaqQueryDTO saveFaqQuery(FaqQueryDTO faqQueryDTO);

	TableResponse getAllFaqQueries(Integer draw, Integer start, Integer length,String search);

	FaqQueryDTO findById(Long id);

	TableResponse getAllQuestion(Integer draw, Integer start, Integer length,String search);

	List<EntryDTO> getFaqEntries();

	List<EntryDTO> getFaqQuestionList();

	FAQ faqById(Long id);

	void deleteQueryResponse(Long id);

	List<FaqQueryDTO> getByFarmer(String farmerId);
}
