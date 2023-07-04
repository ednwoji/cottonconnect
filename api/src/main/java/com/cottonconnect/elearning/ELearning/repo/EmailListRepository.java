package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.EmailList;

@Repository
public interface EmailListRepository extends JpaRepository<EmailList, Long>{
	List<EmailList> findByEmailIdId(Long id);
	
}
