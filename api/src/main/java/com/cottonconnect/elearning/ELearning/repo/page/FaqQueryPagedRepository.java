package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.FaqQuery;

@Repository
public interface FaqQueryPagedRepository extends PagingAndSortingRepository<FaqQuery, Long> {
    @Query(value = "From FaqQuery f where (lower(f.farmer.name) like %:search%  or lower(f.farmer.farmGroup.name) like %:search% or lower(f.farmer.country.name) like %:search%)")
    Page<FaqQuery> findAllWithPage(
            String search,
            Pageable pageable);
}
