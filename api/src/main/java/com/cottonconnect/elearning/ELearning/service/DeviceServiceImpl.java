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

import com.cottonconnect.elearning.ELearning.dto.RegisterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Brand;
import com.cottonconnect.elearning.ELearning.model.Country;
import com.cottonconnect.elearning.ELearning.model.Device;
import com.cottonconnect.elearning.ELearning.model.FarmGroup;
import com.cottonconnect.elearning.ELearning.model.Farmer;
import com.cottonconnect.elearning.ELearning.model.Register;
import com.cottonconnect.elearning.ELearning.repo.BrandRepository;
import com.cottonconnect.elearning.ELearning.repo.CountryRepository;
import com.cottonconnect.elearning.ELearning.repo.DeviceRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.LearnerGroupRepository;
import com.cottonconnect.elearning.ELearning.repo.ProgrammeRepository;
import com.cottonconnect.elearning.ELearning.repo.page.DevicePagedRepo;
import com.cottonconnect.elearning.ELearning.repo.page.RegisterPagedRepo;
import com.utility.Mapper;

@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DevicePagedRepo devicePagedRepository;
	@Autowired
	private FarmerRepository farmerRepository;
	@Autowired
	private RegisterPagedRepo registerRepo;
	@Autowired
	private FarmGroupRepository farmGroupRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ProgrammeRepository programRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private LearnerGroupRepository learnerGroupRepository;

	@Override
	public Device saveDevice(Device device, String userId) {
		Device eDevice = deviceRepository.findByImei(device.getImei());
		if (eDevice != null) {
			device.setId(eDevice.getId());
		}
		Farmer farmer = farmerRepository.findByMobileNumber(device.getMobileNo());
		Mapper.setAuditable(device, userId);
		device.setFarmer(farmer);
		deviceRepository.save(device);
		return device;
	}

	@Override
	public TableResponse getTableDevices(Integer draw, Integer pageNo, Integer pageSize) {
		TableResponse response = null;
		List<List<Object>> roleDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Device> devicePaged = devicePagedRepository.findAll(paging);
		if (devicePaged.hasContent()) {
			List<Device> deviceList = devicePaged.getContent();
			roleDtoList = deviceList.stream().map(device -> {
				List<Object> deviceObjList = new ArrayList<>();
				deviceObjList.add(device.getManufacturer());
				deviceObjList.add(device.getMobileModel());
				deviceObjList.add(device.getOsVersion());
				deviceObjList.add(device.getImei());
				deviceObjList.add(device.getSerialNo());
				deviceObjList.add(device.getFarmer() != null ? device.getFarmer().getName() : "");
				deviceObjList.add(device.getFarmer() != null ? device.getFarmer().getMobileNumber() : "");
				StringBuilder sb = new StringBuilder();
				sb.append(
						"<button class='btn btn-primary btn-sm top-right-button mr-1 simple-icon-pencil' onclick=edit('"
								+ device.getId() + "')></button>");
				/*
				 * sb.
				 * append("<button class='btn btn-danger btn-sm simple-icon-trash' onclick=deletez('"
				 * + role.getId() + "')></button>");
				 */
				deviceObjList.add(sb.toString());
				return deviceObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, devicePaged.getTotalElements(), devicePaged.getTotalElements(),
					roleDtoList);
		} else {
			response = new TableResponse(draw, devicePaged.getTotalElements(), devicePaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public TableResponse getAllUnRegister(Integer draw, Integer pageNo, Integer pageSize) {

		TableResponse response = null;
		List<List<Object>> registerDtoList = new ArrayList<List<Object>>();
		pageNo = pageNo / pageSize;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Register> registerPaged = registerRepo.findAll(paging);
		if (registerPaged.hasContent()) {
			List<Register> registerList = registerPaged.getContent();
			registerDtoList = registerList.stream().map(device -> {
				List<Object> deviceObjList = new ArrayList<>();
				deviceObjList.add("<a href='unregister.jsp?id=" + device.getId() + "' class='detail'>"
						+ device.getName() + "</a>");
				deviceObjList.add(device.getOrganization());
				deviceObjList.add(device.getCountryName());
				deviceObjList.add(device.getMobileNumber());
				deviceObjList.add(device.getLat());
				deviceObjList.add(device.getLon());
				deviceObjList.add(device.getImei());
				deviceObjList.add(device.getManufacturer());
				if (device.isApproved()) {
					deviceObjList.add("Approved");
				} else {
					deviceObjList.add("Waiting");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<button class='btn btn-danger btn-sm top-right-button mr-1 simple-icon-trash' onclick=edit('"
						+ device.getId() + "')></button>");

				deviceObjList.add(sb.toString());
				return deviceObjList;
			}).collect(Collectors.toList());

			response = new TableResponse(draw, registerPaged.getTotalElements(), registerPaged.getTotalElements(),
					registerDtoList);
		} else {
			response = new TableResponse(draw, registerPaged.getTotalElements(), registerPaged.getTotalElements(),
					new ArrayList<>());
		}
		return response;

	}

	@Override
	public void approve(RegisterDTO masterDTO) {
		Optional<Register> register = registerRepo.findById(masterDTO.getId());
		if (register.isPresent()) {
			Register r = register.get();
			r.setApproved(true);

			if (masterDTO.getLearners() != null) {
				r.setLearnerGroups(learnerGroupRepository.findByIdIn(masterDTO.getLearners()));
			}

			if (masterDTO.getFarmGroups() != null) {
				r.setFarmGroups(farmGroupRepository.findByIdIn(masterDTO.getFarmGroups()));
			}

			if (masterDTO.getPrograms() != null) {
				r.setProgrammes(programRepository.findByIdIn(masterDTO.getPrograms()));
			}

			if (masterDTO.getCountries() != null) {
				List<Country> countryList = countryRepository.findByIdIn(masterDTO.getCountries());
				if (countryList != null) {
					r.setCountries(countryList);
				}
			}

			if (masterDTO.getBrands() != null) {
				List<Brand> brandList = brandRepository.findByIdIn(masterDTO.getBrands());
				if (brandList != null) {
					r.setBrands(brandList);
				}
			}

			registerRepo.save(r);
		}
	}

	@Override
	public RegisterDTO getRegisterById(Long id) {
		RegisterDTO registerDTO = new RegisterDTO();
		Optional<Register> registerOpt = registerRepo.findById(id);
		if (registerOpt.isPresent()) {
			Register register = registerOpt.get();
			String countries = register.getCountries().stream().map(pg -> pg.getName())
					.collect(Collectors.joining(","));
			String brands = register.getBrands().stream().map(pg -> pg.getName()).collect(Collectors.joining(","));
			String programs = register.getProgrammes().stream().map(pg -> pg.getName())
					.collect(Collectors.joining(","));
			String farmGroups = register.getFarmGroups().stream().map(pg -> pg.getName())
					.collect(Collectors.joining(","));

			List<Long> learnerList = register.getLearnerGroups().stream().map(lg -> lg.getId())
					.collect(Collectors.toList());
			List<Long> farmGroupList = register.getFarmGroups().stream().map(farmGroup -> farmGroup.getId())
					.collect(Collectors.toList());
			List<Long> programList = register.getProgrammes().stream().map(program -> program.getId())
					.collect(Collectors.toList());
			List<Long> countriesList = register.getCountries().stream().map(country -> country.getId())
					.collect(Collectors.toList());
			List<Long> brandList = register.getBrands().stream().map(brand -> brand.getId())
					.collect(Collectors.toList());

			List<Long> partnerList = new ArrayList<>();
			for (FarmGroup fg : register.getFarmGroups()) {
				partnerList.add(fg.getLocalPartner().getId());
			}

			registerDTO.setId(register.getId());
			registerDTO.setApproved(register.isApproved());
			registerDTO.setBrandName(brands);
			registerDTO.setCountryName(register.getCountryName());
			registerDTO.setFarmGroupName(farmGroups);
			registerDTO.setProgramName(programs);
			registerDTO.setImei(register.getImei());

			registerDTO.setBrands(brandList);
			registerDTO.setCountries(countriesList);
			registerDTO.setLearners(learnerList);
			registerDTO.setFarmGroups(farmGroupList);
			registerDTO.setPrograms(programList);
			registerDTO.setLocalPartners(partnerList);
			registerDTO.setName(register.getName());
			registerDTO.setMobileNumber(register.getMobileNumber());
			registerDTO.setOrganisation(register.getOrganization());
		}
		return registerDTO;
	}

	@Override
	public RegisterDTO deleteById(Long id) {
		registerRepo.deleteById(id);
		return null;
	}

}
