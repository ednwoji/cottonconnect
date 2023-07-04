package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{
	Video  findByUrlContainingIgnoreCase(String name);
}
