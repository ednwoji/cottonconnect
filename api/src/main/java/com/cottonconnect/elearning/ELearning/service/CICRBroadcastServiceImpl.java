package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.cottonconnect.elearning.ELearning.dto.CICRBroadcastDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.CICRBroadcast;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.District;
import com.cottonconnect.elearning.ELearning.model.State;
import com.cottonconnect.elearning.ELearning.model.Taluk;
import com.cottonconnect.elearning.ELearning.model.Village;
import com.cottonconnect.elearning.ELearning.repo.CICRBroadcastRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.DistrictRepository;
import com.cottonconnect.elearning.ELearning.repo.StateRepository;
import com.cottonconnect.elearning.ELearning.repo.TalukRepository;
import com.cottonconnect.elearning.ELearning.repo.VillageRepository;

@Service
public class CICRBroadcastServiceImpl implements CICRBroadcastService {

	@Autowired
	private CICRBroadcastRepository cicrBroadcastRepository;

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
	public Void saveCICR(
			CICRBroadcastDTO cicrBroadcastDTO,
			String videoUrl,
			String audioUrl,
			String documentUrl) {

		CICRBroadcast cicrBroadcast = new CICRBroadcast();

		if (cicrBroadcastDTO.getCountry() != null) {
			Optional<Country> countryOpt = countryRepository.findById(
					cicrBroadcastDTO.getCountry());
			if (countryOpt.isPresent()) {
				cicrBroadcast.setCountry(countryOpt.get());
			}
		}

		if (cicrBroadcastDTO.getState() != null) {
			Optional<State> stateOpt = stateRepository.findById(
					cicrBroadcastDTO.getState());
			if (stateOpt.isPresent()) {
				cicrBroadcast.setState(stateOpt.get());
			}
		}

		if (cicrBroadcastDTO.getDistrict() != null) {
			Optional<District> districtOpt = districtRepository.findById(
					cicrBroadcastDTO.getDistrict());
			if (districtOpt.isPresent()) {
				cicrBroadcast.setDistrict(districtOpt.get());
			}
		}

		if (cicrBroadcastDTO.getTaluk() != null) {
			Optional<Taluk> talukOpt = talukRepository.findById(
					cicrBroadcastDTO.getTaluk());
			if (talukOpt.isPresent()) {
				cicrBroadcast.setTaluk(talukOpt.get());
			}
		}

		if (cicrBroadcastDTO.getVillage() != null) {
			Optional<Village> villageOpt = villageRepository.findById(
					cicrBroadcastDTO.getVillage());
			if (villageOpt.isPresent()) {
				cicrBroadcast.setVillage(villageOpt.get());
			}
		}

		if (videoUrl != null) {
			cicrBroadcast.setVideoUrl(videoUrl);
		}
		if (audioUrl != null) {
			cicrBroadcast.setAudioUrl(audioUrl);
		}
		if (documentUrl != null) {
			cicrBroadcast.setDocumentUrl(documentUrl);
		}

		cicrBroadcast.setIsActive(true);
		cicrBroadcast.setCreateAt(new Date());
		cicrBroadcast.setIsDeleted(false);
		cicrBroadcastRepository.save(cicrBroadcast);

		return null;
	}

	@Override
	public TableResponse getAllRecords() {
		TableResponse response = null;
		List<List<Object>> cicrBroadcastDtoList = new ArrayList<List<Object>>();
		List<CICRBroadcast> cicrBroadcastPaged = cicrBroadcastRepository.findAll();

		cicrBroadcastDtoList = cicrBroadcastPaged.stream().map(broadcast -> {
			List<Object> cicrBroadcastObjList = new ArrayList<>();


			cicrBroadcastObjList.add(broadcast.getCountry().getName());
			cicrBroadcastObjList.add(broadcast.getState().getName());
			cicrBroadcastObjList.add(broadcast.getDistrict().getName());
			cicrBroadcastObjList.add(broadcast.getTaluk().getName());
			cicrBroadcastObjList.add(broadcast.getVillage().getName());

			cicrBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getVideoUrl() + "'> View </a>");

			cicrBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getAudioUrl() + "'> View </a>");

			cicrBroadcastObjList
					.add("<a class='detail' target='_blank' href='" + broadcast.getDocumentUrl() + "'> View </a>");

			cicrBroadcastObjList
					.add("</button> <button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
							+ broadcast.getId() + "')></button>");
			return cicrBroadcastObjList;
		}).collect(Collectors.toList());

		return new TableResponse(1, cicrBroadcastDtoList.size(), cicrBroadcastDtoList.size(), cicrBroadcastDtoList);
	}

	@Override
	public void delete(Long id) {
		Optional<CICRBroadcast> cicrBroadcast = cicrBroadcastRepository.findById(id);
		if (cicrBroadcast.isPresent()) {
			cicrBroadcastRepository.delete(cicrBroadcast.get());
		}
	}
}
