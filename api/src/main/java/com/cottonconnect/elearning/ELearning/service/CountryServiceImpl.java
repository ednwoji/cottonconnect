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

import com.cottonconnect.elearning.ELearning.dto.CountryDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public CountryDTO saveCountry(CountryDTO country) throws CustomException {
		Country countryObject = new Country();
		if (country.getId() != null) {
			Optional<Country> countryOpt = countryRepository.findById(country.getId());
			if (countryOpt.isPresent()) {
				countryObject.setId(countryOpt.get().getId());
			}
		}
		countryObject.setCode(country.getCode());
		countryObject.setName(country.getName());
		countryObject.setActive(true);
		countryObject.setDeleted(false);
		countryObject.setCreatedDate(new Date());
		countryObject.setUpdatedDate(new Date());
		countryRepository.save(countryObject);
		country.setId(countryObject.getId());
		return country;
	}

	@Override
	public CountryDTO findById(Long id) {
		Optional<Country> countryOptional = countryRepository.findById(id);
		if (countryOptional.isPresent()) {
			CountryDTO countryDto = new CountryDTO();
			countryDto.setId(countryOptional.get().getId());
			countryDto.setName(countryOptional.get().getName());
			return countryDto;
		}
		return null;
	}

	@Override
	public TableResponse getAllCountries(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> countryDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Country> countryPaged = countryRepository.findAll(paging);
		if (countryPaged.hasContent()) {
			List<Country> countryList = countryPaged.getContent();
			countryDtoList = countryList.stream().map(country -> {
				List<Object> countryObjList = new ArrayList<>();
				countryObjList.add(country.getCode());
				countryObjList.add(country.getName());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ country.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + country.getId()
						+ "')></button>");
				countryObjList.add(sb.toString());
				return countryObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) countryPaged.getTotalElements(), (int) countryPaged.getTotalElements(),
					countryDtoList);
		} else {
			response = new TableResponse(draw, (int) countryPaged.getTotalElements(), (int) countryPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;
	}

	@Override
	public List<CountryDTO> getCountries() {
		List<CountryDTO> countryList = new ArrayList<CountryDTO>();
		countryRepository.findAll().forEach(country -> {
			CountryDTO countryDto = new CountryDTO();
			countryDto.setCode(country.getCode());
			countryDto.setId(country.getId());
			countryDto.setName(country.getName());
			countryList.add(countryDto);
		});
		return countryList;
	}

	@Override
	public CountryDTO getById(Long id) {
		Optional<Country> countryOpt = countryRepository.findById(id);
		if (countryOpt.isPresent()) {
			Country country = countryOpt.get();
			CountryDTO countryDto = new CountryDTO();
			countryDto.setCode(country.getCode());
			countryDto.setId(country.getId());
			countryDto.setName(country.getName());
			return countryDto;
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		countryRepository.deleteById(id);
	}

}
