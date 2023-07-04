package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.WeatherBroadcastDTO;

public interface WeatherBroadcastService {

	Void saveWeather(
			WeatherBroadcastDTO districtDto,
			String videoUrl,
			String audioUrl,
			String documentUrl);

	TableResponse getAllRecords();

	void delete(Long id);

}
