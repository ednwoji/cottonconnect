package com.cottonconnect.elearning.ELearning.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.SeasonDTO;
import com.cottonconnect.elearning.ELearning.model.Season;
import com.cottonconnect.elearning.ELearning.repo.SeasonRepository;

@Service
public class SessionServiceImpl implements SeasonService {

	@Autowired
	SeasonRepository seasonRepository;

	@Override
	public SeasonDTO saveSeason(SeasonDTO seasonDto) {
		Season season = new Season();
		season.setName(seasonDto.getName());
		season.setCreatedDate(new Date());
		season.setUpdatedDate(new Date());
		seasonRepository.save(season);
		seasonDto.setId(season.getId());
		return seasonDto;
	}

}
