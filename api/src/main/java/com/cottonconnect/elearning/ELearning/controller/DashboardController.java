
package com.cottonconnect.elearning.ELearning.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cottonconnect.elearning.ELearning.dto.DashboardCount;
import com.cottonconnect.elearning.ELearning.dto.DashboardStats;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryChart;
import com.cottonconnect.elearning.ELearning.exception.CustomException;
import com.cottonconnect.elearning.ELearning.service.DashboardService;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/faq-queries")
	public ResponseEntity<FaqQueryChart> save(HttpServletRequest request) throws CustomException {
		FaqQueryChart faqQuery = dashboardService.getFaqQueryChart();
		return new ResponseEntity<FaqQueryChart>(faqQuery, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/stats")
	public ResponseEntity<DashboardStats> getDashboardStats() throws InterruptedException, ExecutionException{
		DashboardStats stats = dashboardService.getDashboardStats();
		return new ResponseEntity<DashboardStats>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sync")
	public void getDashboardSync() {
		dashboardService.getDashboardSync();
	}
	
	@RequestMapping(value = "/farmerCount")
	public ResponseEntity<List<DashboardCount>> farmerCount(){
		List<DashboardCount> dasboardCount = dashboardService.getFarmerCount();
		return new ResponseEntity<List<DashboardCount>>(dasboardCount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/heart-beat")
	public void onlineUsers(@RequestParam("mobileNo") String mobileNo) {
		dashboardService.updateOnlineUsers(mobileNo);
	}
	
	@RequestMapping(value = "/user-stats")
	public ResponseEntity<List<DashboardCount>> getUserOnlineStatus(){
		List<DashboardCount> dasboardCount = dashboardService.getUserStatusCount();
		return new ResponseEntity<List<DashboardCount>>(dasboardCount, HttpStatus.OK);
	}
}
