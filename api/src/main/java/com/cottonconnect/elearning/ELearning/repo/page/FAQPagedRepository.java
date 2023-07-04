package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.FAQ;

public interface FAQPagedRepository extends PagingAndSortingRepository<FAQ, Long> {

}
