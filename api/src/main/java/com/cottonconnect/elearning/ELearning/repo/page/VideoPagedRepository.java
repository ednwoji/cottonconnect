package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Video;

public interface VideoPagedRepository extends PagingAndSortingRepository<Video, Long> {
    @Query(value = "From Video v where v.type =:type and (lower(v.name) like %:search% or lower(v.description) like %:search% )")
    Page<Video> findAllWithPage(
            Long type,
            String search,
            Pageable pageable
            );
}
