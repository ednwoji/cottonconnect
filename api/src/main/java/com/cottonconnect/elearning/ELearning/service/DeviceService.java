package com.cottonconnect.elearning.ELearning.service;

import com.cottonconnect.elearning.ELearning.dto.RegisterDTO;
import com.cottonconnect.elearning.ELearning.dto.TableResponse;
import com.cottonconnect.elearning.ELearning.model.Device;

public interface DeviceService {
	public Device saveDevice(Device device, String userId);

	public TableResponse getTableDevices(Integer draw, Integer start, Integer length);

	public TableResponse getAllUnRegister(Integer draw, Integer start, Integer length);

	public void approve(RegisterDTO masterDTO);

	public RegisterDTO getRegisterById(Long id);

	public RegisterDTO deleteById(Long id);
}
