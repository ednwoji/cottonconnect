package com.cottonconnect.elearning.ELearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.TalukDTO;
import com.cottonconnect.elearning.ELearning.dto.VillageDTO;
import com.cottonconnect.elearning.ELearning.model.Taluk;
import com.cottonconnect.elearning.ELearning.model.Village;
import com.cottonconnect.elearning.ELearning.repo.VillageRepository;

@Service
public class VillageServiceImpl implements VillageService {

	@Autowired
	TalukService talukService;

	@Autowired
	VillageRepository villageRepository;

	@Override
	public VillageDTO saveVillage(VillageDTO villageDto) {
		Village village = new Village();
		if (villageDto.getId() != null) {
			Optional<Village> villageOpt = villageRepository.findById(villageDto.getId());
			if (villageOpt.isPresent()) {
				village.setId(villageOpt.get().getId());
			}
		}
		village.setCode(villageDto.getCode());
		village.setName(villageDto.getName());
		village.setActive(true);
		TalukDTO talukDto = talukService.findById(villageDto.getTalukId());
		Taluk taluk = new Taluk();
		taluk.setId(talukDto.getId());
		village.setTaluk(taluk);
		villageRepository.save(village);
		villageDto.setId(village.getId());
		villageDto.setTalukName(talukDto.getName());
		return villageDto;

	}

	@Override
	public VillageDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableResponse getAllVillages(Integer draw, Integer pageNo, Integer pageSize, String search) {

		TableResponse response = null;
		List<List<Object>> districtDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Village> villagePaged = villageRepository.findAllWithPage(search.toLowerCase(), paging);
		if (villagePaged.hasContent()) {
			List<Village> villageList = villagePaged.getContent();
			districtDtoList = villageList.stream().map(district -> {
				List<Object> villageObjList = new ArrayList<>();
				villageObjList.add(district.getTaluk().getDistrict().getState().getCountry().getName());
				villageObjList.add(district.getTaluk().getDistrict().getState().getName());
				villageObjList.add(district.getTaluk().getDistrict().getName());
				villageObjList.add(district.getTaluk().getName());
				villageObjList.add(district.getCode());
				villageObjList.add(district.getName());

				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ district.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + district.getId()
						+ "')></button>");
				villageObjList.add(sb.toString());

				return villageObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) villagePaged.getTotalElements(),
					(int) villagePaged.getTotalElements(), districtDtoList);
		} else {
			response = new TableResponse(draw, (int) villagePaged.getTotalElements(),
					(int) villagePaged.getTotalElements(), new ArrayList<>());
		}
		return response;

	}

	@Override
	public VillageDTO getById(Long id) {
		Optional<Village> villageOpt = villageRepository.findById(id);
		if (villageOpt.isPresent()) {
			Village village = villageOpt.get();
			VillageDTO villageDTO = new VillageDTO();
			villageDTO.setCode(village.getCode());
			villageDTO.setId(village.getId());
			villageDTO.setName(village.getName());
			villageDTO.setTalukId(village.getTaluk().getId());
			villageDTO.setSelectedCountryId(village.getTaluk().getDistrict().getState().getCountry().getId());
			villageDTO.setSelectedState(village.getTaluk().getDistrict().getState().getId());
			villageDTO.setSelectedDistrict(village.getTaluk().getDistrict().getId());
			return villageDTO;
		}
		return null;

	}

	@Override
	public List<VillageDTO> getVillageByTaluk(Long talukId) {

		List<VillageDTO> villageList = new ArrayList<VillageDTO>();
		villageRepository.findByTaluk(talukId).forEach(village -> {
			VillageDTO villageDto = new VillageDTO();
			villageDto.setCode(village.getCode());
			villageDto.setId(village.getId());
			villageDto.setName(village.getName());
			villageList.add(villageDto);
		});
		return villageList;

	}

	@Override
	public void delete(Long id) {
		villageRepository.deleteById(id);
	}

}
