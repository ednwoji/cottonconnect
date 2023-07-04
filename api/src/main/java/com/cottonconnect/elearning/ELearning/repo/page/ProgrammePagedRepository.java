package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Programme;

@Repository
public interface ProgrammePagedRepository extends PagingAndSortingRepository<Programme, Long> {
	Page<Programme> findByIsDeletedFalse(Pageable pageable);
}
