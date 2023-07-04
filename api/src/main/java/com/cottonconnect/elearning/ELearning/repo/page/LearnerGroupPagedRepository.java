package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.cottonconnect.elearning.ELearning.model.LearnerGroup;

public interface LearnerGroupPagedRepository extends PagingAndSortingRepository<LearnerGroup, Long> {
    @Query(value = "From LearnerGroup l  where (lower(l.name) like %:search%  or lower(l.brand.name) like %:search% or lower(l.programme.name) like %:search% or lower(l.farmGroup.name) like %:search%)")
    Page<LearnerGroup> findAllWithPage(
            String search,
            Pageable pageable);
}
