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

import com.cottonconnect.elearning.ELearning.dto.BrandDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.Programme;
import com.cottonconnect.elearning.ELearning.model.User;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.UserRepository;
import com.cottonconnect.elearning.ELearning.repo.page.BrandPagedRepository;
import com.utility.Mapper;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	BrandRepository brandRepository;
	@Autowired
	BrandPagedRepository brandPagedRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProgrammeRepository programRepository;

	@Override
	public BrandDTO saveBrand(BrandDTO brandDto, String userId) {
		Brand brand = new Brand();
		List<Country> countriesList = new ArrayList<>();
		brand = Mapper.map(brandDto, Brand.class);
		List<User> userList = userRepository.findAllById(brandDto.getUserList());
		if (userList != null) {
			brand.setUsers(userList);
		}
		List<Programme> programList = programRepository.findAllById(brandDto.getProgramsList());
		if (programList != null) {
			brand.setProgrammes(programList);
		}
		countryRepository.findAllById(brandDto.getCountriesList()).forEach(country -> {
			countriesList.add(country);
		});
		brand.setCountries(countriesList);
		Mapper.setAuditable(brand, userId);
		brandRepository.save(brand);
		brandDto.setId(brand.getId());
		return brandDto;
	}

	@Override
	public TableResponse getAllBrands(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> brandDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Brand> brandPaged = brandPagedRepository.findByIsDeletedFalse(paging);
		if (brandPaged.hasContent()) {
			List<Brand> brandList = brandPaged.getContent();
			brandDtoList = brandList.stream().map(brand -> {
				List<Object> brandObjList = new ArrayList<>();
				brandObjList.add("<a href='brandDetail.jsp?id=" + brand.getId() + "' class='detail'>" + brand.getName()
						+ "</a>");
				brandObjList.add(brand.getCountries().stream().map(country -> country.getName())
						.collect(Collectors.joining(",")));
				brandObjList.add(brand.getContactPersonName());
				brandObjList.add(brand.getMobileNo());
				brandObjList.add(brand.getLandLineNo());
				brandObjList.add(brand.getEmail());
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ brand.getId() + "')></button>");

				sb.append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('" + brand.getId()
						+ "')></button>");
				brandObjList.add(sb.toString());
				return brandObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, (int) brandPaged.getTotalElements(), (int) brandPaged.getTotalElements(),
					brandDtoList);
		} else {
			response = new TableResponse(draw, (int) brandPaged.getTotalElements(), (int) brandPaged.getTotalElements(),
					null);
		}
		return response;
	}

	@Override
	public List<BrandDTO> getBrands() {
		List<Brand> brandList = brandRepository.findAll();
		List<BrandDTO> brandDtoList = brandList.stream().map(brand -> {
			BrandDTO brandDto = new BrandDTO();
			brandDto.setId(brand.getId());
			brandDto.setName(brand.getName());
			return brandDto;
		}).collect(Collectors.toList());
		return brandDtoList;
	}

	@Override
	public BrandDTO getById(Long id) {
		Optional<Brand> brandOpt = brandRepository.findById(id);
		BrandDTO brandDTO = new BrandDTO();
		if (brandOpt.isPresent()) {
			Brand brand = brandOpt.get();
			brandDTO.setId(brand.getId());
			brandDTO.setContactNo(brand.getContactNo());
			brandDTO.setContactPersonName(brand.getContactPersonName());
			brandDTO.setCountries(
					brand.getCountries().stream().map(country -> country.getName()).collect(Collectors.joining(",")));
			brandDTO.setCountriesList(
					brand.getCountries().stream().map(country -> country.getId()).collect(Collectors.toList()));
			brandDTO.setEmail(brand.getEmail());
			brandDTO.setLandLineNo(brand.getLandLineNo());
			brandDTO.setMobileNo(brand.getMobileNo());
			brandDTO.setName(brand.getName());
			brandDTO.setPrograms(
					brand.getProgrammes().stream().map(program -> program.getName()).collect(Collectors.joining(",")));
			brandDTO.setProgramsList(
					brand.getProgrammes().stream().map(program -> program.getId()).collect(Collectors.toList()));
			brandDTO.setUserList(brand.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList()));
			brandDTO.setUsers(brand.getUsers().stream().map(user -> user.getName()).collect(Collectors.joining(",")));
		}
		return brandDTO;
	}

	@Override
	public void deleteBrand(Long id) {
		Optional<Brand> brandOpt = brandRepository.findById(id);
		if (brandOpt.isPresent()) {
			brandOpt.get().setActive(false);
			brandOpt.get().setDeleted(true);
			brandRepository.save(brandOpt.get());
		}
	}

	@Override
	public List<BrandDTO> getByCountryId(Long id) {
		List<Long> countryList = new ArrayList<>();
		countryList.add(id);
		List<Brand> brandList = brandRepository.findByCountriesIdIn(countryList);
		List<BrandDTO> brandDtoList = brandList.stream().map(brand -> {
			BrandDTO brandDTO = new BrandDTO();
			brandDTO.setId(brand.getId());
			brandDTO.setName(brand.getName());
			return brandDTO;
		}).collect(Collectors.toList());
		return brandDtoList;
	}

	@Override
	public List<BrandDTO> getByCountryIds(List<Long> id) {
		List<Brand> brandList = brandRepository.findByCountriesIdIn(id);
		List<BrandDTO> brandDtoList = brandList.stream().map(brand -> {
			BrandDTO brandDTO = new BrandDTO();
			brandDTO.setId(brand.getId());
			brandDTO.setName(brand.getName());
			return brandDTO;
		}).collect(Collectors.toList());
		return brandDtoList;
	}

}
