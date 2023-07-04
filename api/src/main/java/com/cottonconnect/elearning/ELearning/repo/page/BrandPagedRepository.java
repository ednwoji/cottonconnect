package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;

public interface BrandPagedRepository extends PagingAndSortingRepository<Brand, Long> {
	Page<Brand> findByIsDeletedFalse(Pageable pageable);
}
