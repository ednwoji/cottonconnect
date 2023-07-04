package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cottonconnect.elearning.ELearning.model.Notification;

public interface NotificationPagedRepository extends PagingAndSortingRepository<Notification, Long> {
    @Query(value = "From Notification n where (lower(n.msg) like %:search%  or lower(n.createdUser) like %:search% )")
    Page<Notification> findAllWithPage(
            String search,
            Pageable pageable
            );
}
