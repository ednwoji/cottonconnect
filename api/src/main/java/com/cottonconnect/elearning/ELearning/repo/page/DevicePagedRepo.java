package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Device;

public interface DevicePagedRepo extends PagingAndSortingRepository<Device, Long>{

}
