package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Device;

public interface DevicePagedRepo extends PagingAndSortingRepository<Device, Long> {
    @Query(value = "From Device d where (lower(d.manufacturer) like %:search%  or lower(d.mobileModel) like %:search% or lower(d.osVersion) like %:search% or lower(d.imei) like %:search% or lower(d.serialNo) like %:search% or lower(d.farmer.name) like %:search% or lower(d.farmer.mobileNumber) like %:search%)")
    Page<Device> findAllWithPage(
            String search,
            Pageable pageable);
}
