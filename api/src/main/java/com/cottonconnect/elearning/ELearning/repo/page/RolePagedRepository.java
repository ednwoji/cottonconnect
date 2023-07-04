package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Role;

@Repository
public interface RolePagedRepository extends PagingAndSortingRepository<Role, Long> {
@Query( value = "From Role r where (lower(r.name) like %:search% )")
    Page<Role> findAllWithPage(
		String search,
		Pageable pageable
		);
}
