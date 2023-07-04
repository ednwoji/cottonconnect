package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.State;

public interface StateRepository extends PagingAndSortingRepository<State, Long>{
	@Query(value = "From State s where s.country.id=:country")
	List<State> findByCountry(Long country);

	@Query( value = "From State s where (lower(s.name) like %:search% or lower(s.country.name) like  %:search% or lower(s.code) like  %:search%)")
    Page<State> findAllWithPage(
		String search,
		Pageable pageable
		);
	
}
