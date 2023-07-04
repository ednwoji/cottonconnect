package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.cottonconnect.elearning.ELearning.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.UploadService;

@RestController
@RequestMapping(value = "/upload")
@Slf4j
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@Value("${farmer_upload_url}")
	private String farmerUrl;

	@Value("${lp_upload_url}")
	private String lp_upload_url;

	@Value("${farmer_group_upload_url}")
	private String farmer_group_upload_url;

	@Value("${lg_upload_url}")
	private String lg_upload_url;

	@PostMapping("/farmer")
	public void uploadFarmer(@RequestParam("file") MultipartFile file,
							 @RequestParam("country") Long country,
							 @RequestParam("brand") Long brand,
							 @RequestParam("program") Long program,
							 @RequestParam("localPartner") Long localPartner,
							 @RequestParam("farmGroup") Long farmGroup,
							 @RequestParam("learnerGroup") Long learnerGroup,
							 @RequestParam("state") Long state,
							 @RequestParam("district") Long district,
							 @RequestParam("taluk") Long taluk,
							 HttpServletResponse res) throws IOException, CustomException {

		try {
			UploadFarmerDTO uploadFarmerDto = new UploadFarmerDTO();
			uploadFarmerDto.setBrand(brand);
			uploadFarmerDto.setCountry(country);
			uploadFarmerDto.setDistrict(district);
			uploadFarmerDto.setTaluk(taluk);
			uploadFarmerDto.setState(state);
			uploadFarmerDto.setLearnerGroup(learnerGroup);
			uploadFarmerDto.setFarmGroup(farmGroup);
			uploadFarmerDto.setLocalPartner(localPartner);
			uploadFarmerDto.setProgram(program);

			List<UploadFarmerExcelDTO> excelData = getExcelData(file);
			uploadService.saveUploadFarmer(excelData, uploadFarmerDto);
			res.sendRedirect(farmerUrl+"?status=success");
		} catch (Exception e) {
			res.sendRedirect(farmerUrl+"?status=failure");
		}
	}



	private List<UploadFarmerExcelDTO> getExcelData(
			MultipartFile file) throws IOException, CustomException {
		log.info("We're reading the Excel file");
		List<UploadFarmerExcelDTO> farmerExcelList = new ArrayList<UploadFarmerExcelDTO>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);


		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			UploadFarmerExcelDTO farmerExcel = new UploadFarmerExcelDTO();

			XSSFRow row = worksheet.getRow(i);

			farmerExcel.setSNo(getCellValue(row, 0));
			farmerExcel.setPuCode(getCellValue(row, 1));
			farmerExcel.setFarmerName(getCellValue(row, 2));
			farmerExcel.setFieldExecutiveName(getCellValue(row, 3));
			farmerExcel.setFarmerCode(getCellValue(row, 4));
			farmerExcel.setFarmerMobileNumber(getCellValue(row, 5));
			farmerExcelList.add(farmerExcel);
		}

		log.info("Farmer List is "+farmerExcelList.toString());
		workbook.close();
		return farmerExcelList;
	}

	private String getCellValue(
			XSSFRow row,
			Integer index) throws CustomException {

		try {

			String cellValue = null;
			XSSFCell cell = row.getCell(index);
			if (cell.getCellTypeEnum().name() == "STRING") {
				cellValue = cell.getStringCellValue();
			} else if (cell.getCellTypeEnum().name() == "NUMERIC") {
				Double cellNumericValue = cell.getNumericCellValue();
				cellValue = String.valueOf(Math.round(cellNumericValue));
			}
			if (cellValue == null || cellValue.isEmpty()) {
				throw new CustomException(
						row.getCell(index).getAddress().toString() + " Value is Empty. Please Fill the Value");
			}
			return cellValue;


		}catch (Exception e){
			log.info(e.getMessage());
			return null;
		}
	}



	@PostMapping("/lp")
	public void uploadLocalPartner(@RequestParam("file") MultipartFile file,
								   @RequestParam("country") Long country,
								   @RequestParam("brand") Long brand,
								   @RequestParam("program") Long program,
							 HttpServletResponse res) throws IOException, CustomException {

		try {
			UploadLpDTO uploadLpDTO = new UploadLpDTO();
			uploadLpDTO.setBrand(brand);
			uploadLpDTO.setProgram(program);
			uploadLpDTO.setCountry(country);

			List<UploadLpExcelDTO> excelData = getLpExcelData(file);
			uploadService.saveLpUploadFarmer(excelData, uploadLpDTO);
			res.sendRedirect(lp_upload_url+"?status=success");
		} catch (Exception e) {
			res.sendRedirect(lp_upload_url+"?status=failure");
		}
	}


	private List<UploadLpExcelDTO> getLpExcelData(
			MultipartFile file) throws IOException, CustomException {
		log.info("We're reading the Excel file for LP upload");
		List<UploadLpExcelDTO> farmerExcelList = new ArrayList<UploadLpExcelDTO>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);


		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			UploadLpExcelDTO farmerExcel = new UploadLpExcelDTO();

			XSSFRow row = worksheet.getRow(i);

			farmerExcel.setName(getCellValue(row, 0));
			farmerExcel.setAddress(getCellValue(row, 1));
			farmerExcelList.add(farmerExcel);
		}

		log.info("Farmer List is "+farmerExcelList.toString());
		workbook.close();
		return farmerExcelList;
	}




	@PostMapping("/farmGroup")
	public void uploadFarmGroup(@RequestParam("file") MultipartFile file,
								   @RequestParam("country") Long country,
								   @RequestParam("brand") Long brand,
								   @RequestParam("program") Long program,
								   @RequestParam("localPartner") Long localPartner,
								   HttpServletResponse res) throws IOException, CustomException {

		try {
			UploadFarmGroupDTO uploadFgDTO = new UploadFarmGroupDTO();
			uploadFgDTO.setBrand(brand);
			uploadFgDTO.setProgram(program);
			uploadFgDTO.setCountry(country);
			uploadFgDTO.setLocalPartner(localPartner);

			List<UploadFarmGroupExcelDTO> excelData = getFarmGroupExcelData(file);
			uploadService.saveFgUploadFarmer(excelData, uploadFgDTO);
			res.sendRedirect(farmer_group_upload_url+"?status=success");
		} catch (Exception e) {
			res.sendRedirect(farmer_group_upload_url+"?status=failure");
		}
	}


	private List<UploadFarmGroupExcelDTO> getFarmGroupExcelData(
			MultipartFile file) throws IOException, CustomException {
		log.info("We're reading the Excel file for Farm Group upload");
		List<UploadFarmGroupExcelDTO> farmerExcelList = new ArrayList<UploadFarmGroupExcelDTO>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);


		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			UploadFarmGroupExcelDTO farmerExcel = new UploadFarmGroupExcelDTO();

			XSSFRow row = worksheet.getRow(i);

			farmerExcel.setName(getCellValue(row, 0));
			farmerExcel.setType(Long.parseLong(getCellValue(row, 1)));
			farmerExcel.setLatitude(getCellValue(row, 2));
			farmerExcel.setLongitude(getCellValue(row, 3));
			farmerExcel.setNoOfFarmers(Long.parseLong(getCellValue(row, 4)));
			farmerExcel.setAcreage(Long.parseLong(getCellValue(row, 5)));
			farmerExcel.setYield(Long.parseLong(getCellValue(row, 6)));
			farmerExcelList.add(farmerExcel);
		}

		workbook.close();
		return farmerExcelList;
	}



	@PostMapping("/lg")
	public void uploadLocalGroup(@RequestParam("file") MultipartFile file,
								@RequestParam("country") Long country,
								@RequestParam("brand") Long brand,
								@RequestParam("program") Long program,
								@RequestParam("localPartner") Long localPartner,
								@RequestParam("farmGroup") Long farmGroup,
								HttpServletResponse res) throws IOException, CustomException {

		try {
			UploadLearnerGroupDTO uploadLgDTO = new UploadLearnerGroupDTO();
			uploadLgDTO.setBrand(brand);
			uploadLgDTO.setProgram(program);
			uploadLgDTO.setCountry(country);
			uploadLgDTO.setLocalPartner(localPartner);
			uploadLgDTO.setFarmGroup(farmGroup);

			List<UploadLgUploadExcelDTO> excelData = getLearnerGroupExcelData(file);
			uploadService.saveLgUploadFarmer(excelData, uploadLgDTO);
			res.sendRedirect(lg_upload_url+"?status=success");
		} catch (Exception e) {
			res.sendRedirect(lg_upload_url+"?status=failure");
		}
	}


	private List<UploadLgUploadExcelDTO> getLearnerGroupExcelData(
			MultipartFile file) throws IOException, CustomException {
		log.info("We're reading the Excel file for Learner Group upload");
		List<UploadLgUploadExcelDTO> farmerExcelList = new ArrayList<UploadLgUploadExcelDTO>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);


		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			UploadLgUploadExcelDTO farmerExcel = new UploadLgUploadExcelDTO();

			XSSFRow row = worksheet.getRow(i);

//			farmerExcel.setName(getCellValue(row, 0));
			farmerExcel.setCountry(getCellValue(row, 1));
			farmerExcel.setBrand(getCellValue(row, 2));
			farmerExcel.setProgramme(getCellValue(row, 3));
			farmerExcel.setLocalPartner(getCellValue(row, 4));
			farmerExcel.setFarmGroup(getCellValue(row, 5));
			farmerExcel.setName(getCellValue(row, 6));
			farmerExcel.setNoOfFarmers(Long.parseLong(getCellValue(row, 7)));
			farmerExcel.setAcreage(Long.parseLong(getCellValue(row, 8)));
			farmerExcel.setYield(Long.parseLong(getCellValue(row, 9)));

			farmerExcelList.add(farmerExcel);
		}

		workbook.close();
		return farmerExcelList;
	}




}
