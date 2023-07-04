package com.cottonconnect.elearning.ELearning.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;
import java.util.List;


public interface KnowledgeCenterRepository extends JpaRepository<KnowledgeCenter, Long> {
    
}
