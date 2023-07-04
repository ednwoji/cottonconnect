package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Taluk;

public interface TalukRepository extends PagingAndSortingRepository<Taluk, Long>{
	@Query(value = "From Taluk s where s.district.id=:district")
	List<Taluk> findByDistrict(Long district);
}
