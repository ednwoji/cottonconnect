package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{
	List<Brand> findByCountriesIdIn(List<Long> countries);
	List<Brand> findByIdIn(List<Long> id);
	@Query(value = "SELECT program_id from brand_programs where brand_id in :brandId", nativeQuery = true)
	List<Long> getProgramsByBrand(List<Long> brandId);
}
