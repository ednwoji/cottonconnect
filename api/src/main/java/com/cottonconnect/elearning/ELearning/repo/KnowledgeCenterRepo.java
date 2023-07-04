package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.KnowledgeCenter;
import com.cottonconnect.elearning.ELearning.model.SubCategory;

@Repository
public interface KnowledgeCenterRepo extends PagingAndSortingRepository<KnowledgeCenter, Long> {
	@Query(value = "From KnowledgeCenter kc where kc.subCategory.id=:subCategoryId order by updatedDate desc")
	List<KnowledgeCenter> listKnowledgeCenterByCategory(Long subCategoryId);
	
	@Query(value = "From KnowledgeCenter kc join kc.farmGroups fg where kc.subCategory.id=:subCategoryId and fg.id IN (:farmGroupId) order by kc.updatedDate desc")	
	List<KnowledgeCenter> listKnowledgeCenterByCategoryAndFarmGroup(Long subCategoryId, List<Long> farmGroupId);
	
	@Query( value = "select kc From KnowledgeCenter kc join kc.countries c join kc.brands b join kc.programmes p join kc.farmGroups g join kc.learnerGroups lg where kc.subCategory.id=:catId and kc.type=:type and (lower(kc.name) like %:search% or lower(kc.name) like  %:search% or lower(c.name) like  %:search% or lower(b.name) like  %:search% or lower(p.name) like  %:search% or lower(g.name) like  %:search% or lower(lg.name) like  %:search%) GROUP BY kc.id")
    Page<KnowledgeCenter> findAllWithPage(
		Long type,
		Long catId,
		String search,
		Pageable pageable
		);
	
	Page<KnowledgeCenter> findBySubCategory(SubCategory subCategory, Pageable pageable);
	
}
