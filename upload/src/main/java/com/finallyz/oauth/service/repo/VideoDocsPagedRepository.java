package com.finallyz.oauth.service.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.finallyz.oauth.service.domain.VideoDocuments;

@Repository
public interface VideoDocsPagedRepository extends PagingAndSortingRepository<VideoDocuments, Long> {

}
