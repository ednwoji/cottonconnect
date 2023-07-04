package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Entitlements;

public interface EntitlementRepository extends JpaRepository<Entitlements, Long> {
	List<Entitlements> findByNameIn(List<String> name);
	
}
