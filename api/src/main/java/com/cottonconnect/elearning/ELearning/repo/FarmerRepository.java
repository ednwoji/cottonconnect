package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cottonconnect.elearning.ELearning.dto.DashboardCount;
import com.cottonconnect.elearning.ELearning.model.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long>{
	public Farmer findByMobileNumber(String mobileNumber);
	
	@Query(value="SELECT COUNT(f.id),f.programme.name FROM Farmer AS f GROUP BY f.programme.name")
	List<Object[]> getFarmersByProgram();
}
