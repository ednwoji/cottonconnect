package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.LocalPartnerName;

@Repository
public interface LocalPartnerPagedRepository extends PagingAndSortingRepository<LocalPartnerName, Long> {
    @Query(value = "select l From LocalPartnerName l join l.Programs p where (lower(l.name) like %:search% or lower(l.address) like %:search% or lower(l.country.name) like %:search% or lower(p.name) like %:search%)  GROUP BY l.id")
    Page<LocalPartnerName> findAllWithPage(
            String search,
            Pageable pageable);
}
