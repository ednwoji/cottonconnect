package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{

}
