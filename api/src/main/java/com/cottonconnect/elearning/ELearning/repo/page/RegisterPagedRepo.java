package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Register;

public interface RegisterPagedRepo extends PagingAndSortingRepository<Register, Long> {
    @Query(value = "From Register r where (lower(r.name) like %:search%  or lower(r.organization) like %:search% or lower(r.countryName) like %:search% or lower(r.mobileNumber) like %:search% or lower(r.lat) like %:search% or lower(r.lon) like %:search% or lower(r.imei) like %:search% or lower(r.manufacturer) like %:search%)")
    Page<Register> findAllWithPage(
            String search,
            Pageable pageable);

}
