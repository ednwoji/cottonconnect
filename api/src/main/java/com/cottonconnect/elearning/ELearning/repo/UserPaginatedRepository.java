package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.User;

@Repository
public interface UserPaginatedRepository extends PagingAndSortingRepository<User, Long> {
}
