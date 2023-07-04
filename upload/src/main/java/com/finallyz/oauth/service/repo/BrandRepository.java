package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finallyz.oauth.service.domain.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{
	List<Brand> findByIdIn(List<Long> id);
	List<Brand> findByCountriesIdIn(List<Long> countries);
}
