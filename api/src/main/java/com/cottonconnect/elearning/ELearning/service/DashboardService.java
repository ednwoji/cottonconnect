package com.cottonconnect.elearning.ELearning.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.cottonconnect.elearning.ELearning.dto.DashboardCount;
import com.cottonconnect.elearning.ELearning.dto.DashboardStats;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryChart;

public interface DashboardService {
	FaqQueryChart getFaqQueryChart();
	
	DashboardStats getDashboardStats() throws InterruptedException, ExecutionException;
	
	void getDashboardSync();
	
	List<DashboardCount>  getFarmerCount();

	void updateOnlineUsers(String mobileNo);

	List<DashboardCount> getUserStatusCount();
}
