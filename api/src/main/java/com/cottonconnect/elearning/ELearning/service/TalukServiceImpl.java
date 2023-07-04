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

import com.cottonconnect.elearning.ELearning.dto.DistrictDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.dto.TalukDTO;
import com.cottonconnect.elearning.ELearning.model.District;
import com.cottonconnect.elearning.ELearning.model.Taluk;
import com.cottonconnect.elearning.ELearning.repo.TalukRepository;

@Service
public class TalukServiceImpl implements TalukService {
	@Autowired
	TalukRepository talukRepository;
	@Autowired
	DistrictService districtService;

	@Override
	public TalukDTO saveTaluk(TalukDTO talukDto) {
		Taluk taluk = new Taluk();
		if (talukDto.getId() != null) {
			Optional<Taluk> talukOpt = talukRepository.findById(talukDto.getId());
			if (talukOpt.isPresent()) {
				taluk.setId(talukOpt.get().getId());
			}
		}
		taluk.setName(talukDto.getName());
		taluk.setCode(talukDto.getCode());
		taluk.setActive(true);
		taluk.setDeleted(false);
		taluk.setCreatedDate(new Date());
		taluk.setUpdatedDate(new Date());

		DistrictDTO districtDto = districtService.findById(talukDto.getDistrictId());
		District district = new District();
		district.setId(districtDto.getId());

		taluk.setDistrict(district);
		talukRepository.save(taluk);
		talukDto.setId(taluk.getId());
		talukDto.setDistrictName(taluk.getDistrict().getName());
		return talukDto;
	}

	@Override
	public TalukDTO findById(Long id) {
		Optional<Taluk> talukOptional = talukRepository.findById(id);
		if (talukOptional.isPresent()) {
			Taluk taluk = talukOptional.get();
			TalukDTO talukDto = new TalukDTO(taluk.getId(), taluk.getCode(), taluk.getName(),
					taluk.getDistrict().getId(), taluk.getDistrict().getName(), taluk.getDistrict().getState().getId(),
					taluk.getDistrict().getState().getCountry().getId());
			return talukDto;
		}
		return null;

	}

	@Override
	public TableResponse getAllTaluks(Integer draw, Integer pageNo, Integer pageSize,String search) {

		TableResponse response = null;
		List<List<Object>> talukDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Taluk> talukPaged = talukRepository.findAllWithPage(search.toLowerCase(),paging);
		if (talukPaged.hasContent()) {
			List<Taluk> talukList = talukPaged.getContent();
			talukDtoList = talukList.stream().map(taluk -> {
				List<Object> talukObjList = new ArrayList<>();
				talukObjList.add(taluk.getDistrict().getState().getCountry().getName());
				talukObjList.add(taluk.getDistrict().getState().getName());
				talukObjList.add(taluk.getDistrict().getName());
				talukObjList.add(taluk.getCode());
				talukObjList.add(taluk.getName());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ taluk.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + taluk.getId()
						+ "')></button>");
				talukObjList.add(sb.toString());
				return talukObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) talukPaged.getTotalElements(), (int) talukPaged.getTotalElements(),
					talukDtoList);
		} else {
			response = new TableResponse(draw, (int) talukPaged.getTotalElements(), (int) talukPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<TalukDTO> getTaluks(Long districtId) {

		List<TalukDTO> districtList = new ArrayList<TalukDTO>();
		talukRepository.findByDistrict(districtId).forEach(taluk -> {
			TalukDTO talukDto = new TalukDTO();
			talukDto.setCode(taluk.getCode());
			talukDto.setId(taluk.getId());
			talukDto.setName(taluk.getName());
			districtList.add(talukDto);
		});
		return districtList;

	}

	@Override
	public TalukDTO getById(Long id) {
		Optional<Taluk> talukOpt = talukRepository.findById(id);
		if (talukOpt.isPresent()) {
			Taluk taluk = talukOpt.get();
			TalukDTO talukDTO = new TalukDTO();
			talukDTO.setCode(taluk.getCode());
			talukDTO.setId(taluk.getId());
			talukDTO.setName(taluk.getName());
			talukDTO.setDistrictId(taluk.getDistrict().getId());
			talukDTO.setSelectedState(taluk.getDistrict().getState().getId());
			talukDTO.setSelectedCountryId(taluk.getDistrict().getState().getCountry().getId());
			return talukDTO;
		}
		return null;

	}

	@Override
	public void delete(Long id) {
		talukRepository.deleteById(id);
	}

}
