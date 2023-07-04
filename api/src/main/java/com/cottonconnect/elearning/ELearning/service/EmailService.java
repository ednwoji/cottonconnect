package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.EmailDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Email;

public interface EmailService {

	Email save(EmailDTO email);

	TableResponse getEmails(Integer draw, Integer start, Integer length,String search);

	EmailDTO findById(Long valueOf);

	void delete(Long valueOf, String user);

}
