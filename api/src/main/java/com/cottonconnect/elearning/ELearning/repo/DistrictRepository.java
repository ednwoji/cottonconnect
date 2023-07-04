package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.District;

public interface DistrictRepository extends PagingAndSortingRepository<District, Long>{
	@Query(value = "From District s where s.state.id=:state")
	List<District> findByState(Long state);
}
