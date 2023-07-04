package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Role;

@Repository
public interface RolePagedRepository extends PagingAndSortingRepository<Role, Long> {

}
