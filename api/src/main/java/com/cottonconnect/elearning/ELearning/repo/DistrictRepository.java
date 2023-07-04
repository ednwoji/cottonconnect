package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.District;

public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {
	@Query(value = "From District s where s.state.id=:state")
	List<District> findByState(Long state);

	@Query(value = "From District d where (lower(d.name) like %:search% or lower(d.state.name) like %:search% or lower(d.state.country.name) like  %:search% or lower(d.code) like  %:search%)")
	Page<District> findAllWithPage(
			String search,
			Pageable pageable);
}
