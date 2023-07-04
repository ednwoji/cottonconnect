package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cottonconnect.elearning.ELearning.dto.WeatherBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.WeatherBroadcast;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.District;
import com.cottonconnect.elearning.ELearning.model.State;
import com.cottonconnect.elearning.ELearning.model.Taluk;
import com.cottonconnect.elearning.ELearning.model.Village;
import com.cottonconnect.elearning.ELearning.repo.WeatherBroadcastRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.DistrictRepository;
import com.cottonconnect.elearning.ELearning.repo.StateRepository;
import com.cottonconnect.elearning.ELearning.repo.TalukRepository;
import com.cottonconnect.elearning.ELearning.repo.VillageRepository;

@Service
public class WeatherBroadcastServiceImpl implements WeatherBroadcastService {

	@Autowired
	private WeatherBroadcastRepository weatherBroadcastRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private TalukRepository talukRepository;

	@Autowired
	private VillageRepository villageRepository;

	@Override
	public Void saveWeather(
			WeatherBroadcastDTO weatherBroadcastDTO,
			String videoUrl,
			String audioUrl,
			String documentUrl) {

		WeatherBroadcast weatherBroadcast = new WeatherBroadcast();

		if (weatherBroadcastDTO.getCountry() != null) {
			Optional<Country> countryOpt = countryRepository.findById(
					weatherBroadcastDTO.getCountry());
			if (countryOpt.isPresent()) {
				weatherBroadcast.setCountry(countryOpt.get());
			}
		}

		if (weatherBroadcastDTO.getState() != null) {
			Optional<State> stateOpt = stateRepository.findById(
					weatherBroadcastDTO.getState());
			if (stateOpt.isPresent()) {
				weatherBroadcast.setState(stateOpt.get());
			}
		}

		if (weatherBroadcastDTO.getDistrict() != null) {
			Optional<District> districtOpt = districtRepository.findById(
					weatherBroadcastDTO.getDistrict());
			if (districtOpt.isPresent()) {
				weatherBroadcast.setDistrict(districtOpt.get());
			}
		}

		if (weatherBroadcastDTO.getTaluk() != null) {
			Optional<Taluk> talukOpt = talukRepository.findById(
					weatherBroadcastDTO.getTaluk());
			if (talukOpt.isPresent()) {
				weatherBroadcast.setTaluk(talukOpt.get());
			}
		}

		if (weatherBroadcastDTO.getVillage() != null) {
			Optional<Village> villageOpt = villageRepository.findById(
					weatherBroadcastDTO.getVillage());
			if (villageOpt.isPresent()) {
				weatherBroadcast.setVillage(villageOpt.get());
			}
		}

		if (weatherBroadcastDTO.getId() != null) {
			weatherBroadcast.setId(weatherBroadcastDTO.getId());
			Optional<WeatherBroadcast> foundWeatherBroadcast = weatherBroadcastRepository.findById(weatherBroadcastDTO.getId());
			if (foundWeatherBroadcast.isPresent()) {
				weatherBroadcast.setIsActive(foundWeatherBroadcast.get().getIsActive());
				weatherBroadcast.setCreateAt(foundWeatherBroadcast.get().getCreateAt());
				weatherBroadcast.setIsDeleted(foundWeatherBroadcast.get().getIsDeleted());
				weatherBroadcast.setVideoUrl(foundWeatherBroadcast.get().getVideoUrl());
				weatherBroadcast.setAudioUrl(foundWeatherBroadcast.get().getAudioUrl());
				weatherBroadcast.setDocumentUrl(foundWeatherBroadcast.get().getDocumentUrl());
			}
		} else {
			weatherBroadcast.setIsActive(true);
			weatherBroadcast.setCreateAt(new Date());
			weatherBroadcast.setIsDeleted(false);
		}


		if (videoUrl != null) {
			weatherBroadcast.setVideoUrl(videoUrl);
		}
		if (audioUrl != null) {
			weatherBroadcast.setAudioUrl(audioUrl);
		}
		if (documentUrl != null) {
			weatherBroadcast.setDocumentUrl(documentUrl);
		}

		weatherBroadcast.setIsActive(true);
		weatherBroadcast.setCreateAt(new Date());
		weatherBroadcast.setIsDeleted(false);
		weatherBroadcastRepository.save(weatherBroadcast);

		return null;
	}

	@Override
	public TableResponse getAllRecords() {

		List<List<Object>> weatherBroadcastDtoList = new ArrayList<List<Object>>();

		List<WeatherBroadcast> weatherBroadcastPaged = weatherBroadcastRepository.findAll();

		weatherBroadcastDtoList = weatherBroadcastPaged.stream().map(broadcast -> {
			List<Object> weatherBroadcastObjList = new ArrayList<>();

			weatherBroadcastObjList.add(broadcast.getCountry().getName());
			weatherBroadcastObjList.add(broadcast.getState().getName());
			weatherBroadcastObjList.add(broadcast.getDistrict().getName());
			weatherBroadcastObjList.add(broadcast.getTaluk().getName());
			weatherBroadcastObjList.add(broadcast.getVillage().getName());

			weatherBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getVideoUrl() + "'> View </a>");

			weatherBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getAudioUrl() + "'> View </a>");

			weatherBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getDocumentUrl() + "'> View </a>");

			weatherBroadcastObjList
					.add("<button class='btn btn-success btn-sm simple-icon-pencil'onclick=editz("+ broadcast.getId() + ")></button> <button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
							+ broadcast.getId() + "')></button>");
			return weatherBroadcastObjList;
		}).collect(Collectors.toList());

		return new TableResponse(
				1,
				weatherBroadcastDtoList.size(),
				weatherBroadcastDtoList.size(),
				weatherBroadcastDtoList);

	}

	@Override
	public void delete(Long id) {
		Optional<WeatherBroadcast> weatherBroadcast = weatherBroadcastRepository.findById(id);
		if (weatherBroadcast.isPresent()) {
			weatherBroadcastRepository.delete(weatherBroadcast.get());
		}
	}

	@Override
	public WeatherBroadcastDTO findById(Long id) {
		WeatherBroadcastDTO weatherBroadcast = new WeatherBroadcastDTO();
		Optional<WeatherBroadcast> weatherBroadcastOpt = weatherBroadcastRepository.findById(id);
		if (weatherBroadcastOpt.isPresent()) {
			weatherBroadcast.setCountry(weatherBroadcastOpt.get().getCountry().getId());
			weatherBroadcast.setState(weatherBroadcastOpt.get().getState().getId());
			weatherBroadcast.setDistrict(weatherBroadcastOpt.get().getDistrict().getId());
			weatherBroadcast.setTaluk(weatherBroadcastOpt.get().getTaluk().getId());
			weatherBroadcast.setVillage(weatherBroadcastOpt.get().getVillage().getId());
			weatherBroadcast.setVideoUrl(weatherBroadcastOpt.get().getVideoUrl());
			weatherBroadcast.setAudioUrl(weatherBroadcastOpt.get().getAudioUrl());
			weatherBroadcast.setDocumentUrl(weatherBroadcastOpt.get().getDocumentUrl());
		}

		return weatherBroadcast;
	}
}
