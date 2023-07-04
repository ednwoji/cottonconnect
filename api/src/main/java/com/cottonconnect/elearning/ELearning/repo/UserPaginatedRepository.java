package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.User;

@Repository
public interface UserPaginatedRepository extends PagingAndSortingRepository<User, Long> {
    @Query( value = "From User u where (lower(u.userId) like %:search%  or lower(u.name) like %:search%  or lower(u.emailId) like %:search% or lower(u.mobileNo) like %:search% )")
    Page<User> findAllWithPage(
		String search,
		Pageable pageable
		);
}
