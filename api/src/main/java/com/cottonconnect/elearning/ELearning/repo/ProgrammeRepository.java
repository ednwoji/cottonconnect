package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Programme;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long>{
	List<Programme> findByIdIn(List<Long> ids);
	
}
