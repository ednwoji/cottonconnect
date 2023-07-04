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

import com.cottonconnect.elearning.ELearning.dto.ProgrammeDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.page.ProgrammePagedRepository;
import com.utility.Mapper;

@Service
public class ProgrammeServiceImpl implements ProgrammeService {
	@Autowired
	ProgrammeRepository programmeRepository;
	@Autowired
	ProgrammePagedRepository programmePagedRepository;
	@Autowired
	BrandRepository brandRepository;

	@Override
	public ProgrammeDTO saveProgramme(ProgrammeDTO programmeDTO, String userId) {
		Programme programme = new Programme();
		programme = Mapper.map(programmeDTO, Programme.class);
		Mapper.setAuditable(programme, userId);
		if (programmeDTO.getStatus() == 0L) {
			programme.setActive(false);
		}
		if (programmeDTO.getId() != null) {
			Optional<Programme> programOpt = programmePagedRepository.findById(programmeDTO.getId());
			if (programOpt.isPresent()) {
				programme.setId(programOpt.get().getId());
			}
		}
		programmeRepository.save(programme);
		programmeDTO.setId(programme.getId());
		return programmeDTO;
	}

	@Override
	public TableResponse getAllPrograms(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> countryDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Programme> programPaged = programmePagedRepository.findByIsDeletedFalse(paging);
		if (programPaged.hasContent()) {
			List<Programme> programeList = programPaged.getContent();
			countryDtoList = programeList.stream().map(country -> {
				List<Object> countryObjList = new ArrayList<>();
				countryObjList.add(country.getName());
				countryObjList.add(country.isActive() ? "Active" : "In Active");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ country.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + country.getId()
						+ "')></button>");
				countryObjList.add(sb.toString());
				return countryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, programPaged.getTotalElements(), programPaged.getTotalElements(),
					countryDtoList);
		} else {
			response = new TableResponse(draw, programPaged.getTotalElements(), programPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;
	}

	@Override
	public List<ProgrammeDTO> getPrograms() {
		List<Programme> programList = programmeRepository.findAll();
		List<ProgrammeDTO> programDtoList = programList.stream().map(program -> {
			ProgrammeDTO programDto = Mapper.map(program, ProgrammeDTO.class);
			return programDto;
		}).collect(Collectors.toList());
		return programDtoList;
	}

	@Override
	public List<ProgrammeDTO> getProgramByBrand(Long brandId) {
		Optional<Brand> brandOpt = brandRepository.findById(brandId);
		if (brandOpt.isPresent()) {
			List<ProgrammeDTO> programList = brandOpt.get().getProgrammes().stream().map(program -> {
				ProgrammeDTO programDTO = Mapper.map(program, ProgrammeDTO.class);
				return programDTO;
			}).collect(Collectors.toList());

			return programList;
		}
		return new ArrayList<>();
	}

	@Override
	public ProgrammeDTO getById(Long id) {
		Optional<Programme> programOpt = programmePagedRepository.findById(id);
		if (programOpt.isPresent()) {
			ProgrammeDTO programDto = Mapper.map(programOpt.get(), ProgrammeDTO.class);
			programDto.setStatus(programOpt.get().isActive() ? 1L : 0L);
			return programDto;
		}
		return null;
	}

	@Override
	public void deleteProgram(Long id) {
		Optional<Programme> programOpt = programmePagedRepository.findById(id);
		if (programOpt.isPresent()) {
			programmeRepository.deleteById(id);
		}

	}

	@Override
	public List<ProgrammeDTO> getProgramByBrands(List<Long> brandId) {
		List<Long> programs = brandRepository.getProgramsByBrand(brandId);
		List<Programme> programObjList = programmeRepository.findByIdIn(programs);
		if (programObjList != null) {
			List<ProgrammeDTO> programList = programObjList.stream().map(program -> {
				ProgrammeDTO programDTO = Mapper.map(program, ProgrammeDTO.class);
				return programDTO;
			}).collect(Collectors.toList());

			return programList;
		}
		return new ArrayList<>();
	}

}
