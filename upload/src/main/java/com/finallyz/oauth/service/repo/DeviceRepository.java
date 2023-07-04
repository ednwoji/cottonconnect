package com.finallyz.oauth.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finallyz.oauth.service.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>{
	Device findByImei(String imei);
	
	List<Device> findByFarmerId(Long id);
}
