package com.cottonconnect.elearning.ELearning.repo;

import com.cottonconnect.elearning.ELearning.model.Email_list_new;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailListNewRepository extends JpaRepository<Email_list_new, Long> {
}
