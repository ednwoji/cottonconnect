package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.FarmGroup;

@Repository
public interface FarmGroupPagedRepository extends PagingAndSortingRepository<FarmGroup, Long> {
	Page<FarmGroup> findByIsDeletedFalse(Pageable pageable);
}
