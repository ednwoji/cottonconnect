package com.cottonconnect.elearning.ELearning.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.cottonconnect.elearning.ELearning.dto.KnowledgeCenterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Category;
import com.cottonconnect.elearning.ELearning.model.SubCategory;

public interface KnowleldgeCenterService {
	KnowledgeCenterDTO saveKnowledgeCenter(KnowledgeCenterDTO knowledgeCenterDTO, List<File> imageFiles);

	List<KnowledgeCenterDTO> findAllBySubCategory(Long category);

	FileInputStream getPhoto(String fileName);

	KnowledgeCenterDTO findById(Long id);

	TableResponse getAllRecords(
			Integer draw,
			Integer start,
			Integer length,
			Long catId,
			Long type,
			String search);

	public void saveSubCategory(List<SubCategory> subCategories);

	public void saveCategory(List<Category> categories);

	List<SubCategory> getSubCategoryList();

	void delete(Long id);

	void deleteByImageId(Long id);

	List<KnowledgeCenterDTO> findAllBySubCategory(Long valueOf, String mobile);
}
