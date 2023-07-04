package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.FaqQuery;
import com.cottonconnect.elearning.ELearning.model.LearnerGroup;

public interface LearnerGroupPagedRepository extends PagingAndSortingRepository<LearnerGroup, Long>{

}
