package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{

}
