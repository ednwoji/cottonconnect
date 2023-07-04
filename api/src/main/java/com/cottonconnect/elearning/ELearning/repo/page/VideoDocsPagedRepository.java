package com.cottonconnect.elearning.ELearning.repo.page;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.VideoDocuments;

@Repository
public interface VideoDocsPagedRepository extends PagingAndSortingRepository<VideoDocuments, Long> {

}
