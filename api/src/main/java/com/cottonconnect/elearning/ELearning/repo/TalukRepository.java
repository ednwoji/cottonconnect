package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Taluk;

public interface TalukRepository extends PagingAndSortingRepository<Taluk, Long> {
	@Query(value = "From Taluk s where s.district.id=:district")
	List<Taluk> findByDistrict(Long district);

	@Query(value = "From Taluk t where (lower(t.name) like %:search%  or lower(t.district.name) like %:search% or lower(t.district.state.name) like %:search% or lower(t.district.state.country.name) like %:search% or lower(t.code) like  %:search%)")
	Page<Taluk> findAllWithPage(
			String search,
			Pageable pageable);
}
