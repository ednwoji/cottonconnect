package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Category;
import com.cottonconnect.elearning.ELearning.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
	List<SubCategory> findByCategory(Category category);
}
