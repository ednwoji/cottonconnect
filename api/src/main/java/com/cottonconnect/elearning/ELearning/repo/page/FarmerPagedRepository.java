package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Farmer;

public interface FarmerPagedRepository extends PagingAndSortingRepository<Farmer, Long> {

}
