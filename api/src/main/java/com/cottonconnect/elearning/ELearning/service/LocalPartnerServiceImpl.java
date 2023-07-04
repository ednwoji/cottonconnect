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

import com.cottonconnect.elearning.ELearning.dto.LocalPartnerNameDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.LocalPartnerName;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.LocalPartnerRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.page.LocalPartnerPagedRepository;
import com.utility.Mapper;

@Service
public class LocalPartnerServiceImpl implements LocalPartnerService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ProgrammeRepository programRepository;

	@Autowired
	private LocalPartnerRepository localPartnerRepository;

	@Autowired
	private LocalPartnerPagedRepository localPartnerPagedRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public LocalPartnerNameDTO saveLocalPartner(LocalPartnerNameDTO LocalPartnerNameDto, String userId) {
		LocalPartnerName localPartner = Mapper.map(LocalPartnerNameDto, LocalPartnerName.class);
		Optional<Country> countryOpt = countryRepository.findById(LocalPartnerNameDto.getCountryId());
		if (countryOpt.isPresent()) {
			localPartner.setCountry(countryOpt.get());
		}
		Optional<Brand> brandOpt = brandRepository.findById(LocalPartnerNameDto.getBrandId());
		if (brandOpt.isPresent()) {
			localPartner.setBrand(brandOpt.get());
		}
		List<Programme> programList = programRepository.findAllById(LocalPartnerNameDto.getProgramId());
		localPartner.setPrograms(programList);
		Mapper.setAuditable(localPartner, userId);
		if (LocalPartnerNameDto.getActive() == 0L) {
			localPartner.setActive(false);
		}
		localPartnerRepository.save(localPartner);
		LocalPartnerNameDto.setId(localPartner.getId());
		return LocalPartnerNameDto;
	}

	@Override
	public TableResponse getAllPartners(Integer draw, Integer pageNo, Integer pageSize,String search) {

		TableResponse response = null;
		List<List<Object>> partnerDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<LocalPartnerName> partnerPaged = localPartnerPagedRepository.findAllWithPage(search.toLowerCase(),paging);
		if (partnerPaged.hasContent()) {
			List<LocalPartnerName> partnerList = partnerPaged.getContent();
			partnerDtoList = partnerList.stream().map(partner -> {
				List<Object> partnerObjList = new ArrayList<>();
				partnerObjList.add("<a href='lpaDetail.jsp?id=" + partner.getId() + "' class='detail'>"
						+ partner.getName() + "</a>");
				partnerObjList.add(partner.getAddress());
				String programs = partner.getPrograms().stream().map(program -> program.getName())
						.collect(Collectors.joining(","));
				partnerObjList.add(programs);
				partnerObjList.add(partner.getCountry().getName());
				partnerObjList.add(partner.isActive() ? "Active" : "Inactive");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ partner.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + partner.getId()
						+ "')></button>");
				partnerObjList.add(sb.toString());
				return partnerObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, partnerPaged.getTotalElements(), partnerPaged.getTotalElements(),
					partnerDtoList);
		} else {
			response = new TableResponse(draw, partnerPaged.getTotalElements(), partnerPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public List<LocalPartnerNameDTO> getLocalPartner(Long programId) {
		Programme programme = new Programme();
		programme.setId(programId);
		List<Programme> programeList = new ArrayList<>();
		programeList.add(programme);
		List<LocalPartnerName> partnerList = localPartnerRepository.findByProgram(programId);

		List<LocalPartnerNameDTO> localPartnerDTOList = partnerList.stream().map(partner -> {
			LocalPartnerNameDTO localPartnerDTO = new LocalPartnerNameDTO();
			localPartnerDTO.setId(partner.getId());
			localPartnerDTO.setName(partner.getName());
			return localPartnerDTO;
		}).collect(Collectors.toList());

		return localPartnerDTOList;
	}

	@Override
	public LocalPartnerNameDTO getById(Long id) {

		Optional<LocalPartnerName> partner = localPartnerRepository.findById(id);
		LocalPartnerNameDTO partnerDTO = new LocalPartnerNameDTO();
		if (partner.isPresent()) {
			LocalPartnerName localPartner = partner.get();
			partnerDTO.setId(localPartner.getId());
			partnerDTO.setCountries(localPartner.getCountry().getName());
			partnerDTO.setPrograms(localPartner.getPrograms().stream().map(program -> program.getName())
					.collect(Collectors.joining(",")));
			partnerDTO.setProgramId(
					localPartner.getPrograms().stream().map(program -> program.getId()).collect(Collectors.toList()));
			partnerDTO.setBrandId(localPartner.getBrand().getId());
			partnerDTO.setStatus(localPartner.isActive() ? "Active" : "In Active");
			partnerDTO.setStatusz(localPartner.isActive() ? 1L : 0L);
			partnerDTO.setAddress(localPartner.getAddress());
			partnerDTO.setCountryId(localPartner.getCountry().getId());
			partnerDTO.setName(localPartner.getName());
		}
		return partnerDTO;

	}

	@Override
	public void delete(Long id) {
		localPartnerRepository.deleteById(id);
	}

	@Override
	public List<LocalPartnerNameDTO> getLocalPartners(List<Long> programId) {

		List<LocalPartnerName> partnerList = localPartnerRepository.findByProgramIdIn(programId);

		List<LocalPartnerNameDTO> partnerDtoList = partnerList.stream().map(partner -> {
			LocalPartnerNameDTO partnerDto = new LocalPartnerNameDTO();
			partnerDto.setId(partner.getId());
			partnerDto.setName(partner.getName());
			return partnerDto;
		}).collect(Collectors.toList());

		return partnerDtoList;

	}

}
