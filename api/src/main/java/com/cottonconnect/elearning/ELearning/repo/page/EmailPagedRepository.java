package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Email;

public interface EmailPagedRepository extends PagingAndSortingRepository<Email, Long>{
	Page<Email> findByIsDeletedFalse(Pageable pageable);
}
