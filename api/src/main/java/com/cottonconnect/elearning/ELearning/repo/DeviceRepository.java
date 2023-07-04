package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>{
	Device findByImei(String imei);
	
	List<Device> findByFarmerId(Long id);
}
