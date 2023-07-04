package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Village;

import java.util.List;

public interface VillageRepository extends PagingAndSortingRepository<Village, Long> {

    List<Village> findByName(String name);

    List<Village> findByTaluk(Long talukId);


}
