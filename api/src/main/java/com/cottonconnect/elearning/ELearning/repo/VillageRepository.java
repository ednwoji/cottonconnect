package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.cottonconnect.elearning.ELearning.model.Village;

public interface VillageRepository extends PagingAndSortingRepository<Village, Long> {
    @Query(value = "From Village v where (lower(v.name) like %:search%  or lower(v.taluk.name) like %:search%  or lower(v.taluk.district.name) like %:search% or lower(v.taluk.district.state.name) like %:search% or lower(v.taluk.district.state.country.name) like %:search% or lower(v.code) like  %:search%)")
    Page<Village> findAllWithPage(
            String search,
            Pageable pageable);

    @Query(value = "From Village v where v.taluk.id=:taluk")
    List<Village> findByTaluk(Long taluk);
}
