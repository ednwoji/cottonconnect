package com.finallyz.oauth.service.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.finallyz.oauth.service.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
   
}
