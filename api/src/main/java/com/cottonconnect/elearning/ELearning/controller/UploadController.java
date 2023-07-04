package com.cottonconnect.elearning.ELearning.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerDTO;
import com.cottonconnect.elearning.ELearning.dto.UploadFarmerExcelDTO;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.UploadService;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = "/farmer", method = RequestMethod.POST)
	public ResponseEntity<UploadFarmerExcelDTO> uploadFarmer(
			UploadFarmerDTO uploadFarmerDto,
			@RequestParam("file") MultipartFile file,
			HttpServletResponse res) throws IOException, CustomException {
		List<UploadFarmerExcelDTO> excelData = getExcelData(file);
		uploadService.saveUploadFarmer(excelData, uploadFarmerDto);
		res.sendRedirect(uploadFarmerDto.getRedirectUrl());
		return null;
	}

	private List<UploadFarmerExcelDTO> getExcelData(
			MultipartFile file) throws IOException, CustomException {
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
		workbook.close();
		return farmerExcelList;
	}

	private String getCellValue(
			XSSFRow row,
			Integer index) throws CustomException {
		String cellValue = null;
		XSSFCell cell = row.getCell(index);
		if (cell.getCellTypeEnum().name() == "STRING") {
			cellValue = cell.getStringCellValue();
		} else if (cell.getCellTypeEnum().name() == "NUMERIC") {
			Double cellNumericValue =cell.getNumericCellValue();
			cellValue = String.valueOf(Math.round(cellNumericValue));
		}
		if (cellValue == null || cellValue.isEmpty()) {
			throw new CustomException(
					row.getCell(index).getAddress().toString() + " Value is Empty. Please Fill the Value");
		}
		return cellValue;
	}

}
