package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.FAQ;

public interface FAQPagedRepository extends PagingAndSortingRepository<FAQ, Long> {
    @Query(value = "From FAQ f where (lower(f.question) like %:search%  or lower(f.answer) like %:search% )")
    Page<FAQ> findAllWithPage(
		String search,
		Pageable pageable
		);
}
