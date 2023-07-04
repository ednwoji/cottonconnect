package com.cottonconnect.elearning.ELearning.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cottonconnect.elearning.ELearning.dto.DashboardCount;
import com.cottonconnect.elearning.ELearning.dto.DashboardStats;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryChart;
import com.cottonconnect.elearning.ELearning.model.UserOnline;
import com.cottonconnect.elearning.ELearning.repo.FaqResponseRepo;
import com.cottonconnect.elearning.ELearning.repo.FarmerRepository;
import com.cottonconnect.elearning.ELearning.repo.KnowledgeCenterRepo;
import com.cottonconnect.elearning.ELearning.repo.NotificationRepository;
import com.cottonconnect.elearning.ELearning.repo.RegisterRepository;
import com.cottonconnect.elearning.ELearning.repo.UserOnlineRepository;
import com.cottonconnect.elearning.ELearning.repo.VideoRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	@Autowired
	private FaqResponseRepo faqResponseRepo;
	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private KnowledgeCenterRepo knowledgeCenterRepo;
	@Autowired
	private VideoRepository videoRepo;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private FarmerRepository farmerRepository;
	@Autowired
	private UserOnlineRepository userOnlineRepository;

	@Override
	public FaqQueryChart getFaqQueryChart() {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -30);
		Date oneMonthBack = cal.getTime();
		
		FaqQueryChart faqQuery = new FaqQueryChart();
		
		List<Object[]> queriesReceivedList = faqResponseRepo.getQueriesReceived(oneMonthBack, currentDate);
		
		List<Object[]> queriesRespondedList = faqResponseRepo.getQueriesResponded(oneMonthBack, currentDate);
		
		List<String> dates = new ArrayList<>();
		List<Integer> queryReceived = new ArrayList<>();
		List<Integer> queryResponded = new ArrayList<>();
		
		DateFormat df = new SimpleDateFormat("dd/MM/YY");
		
		for(int i=0;i<queriesReceivedList.size();i++) {
			BigInteger receivedCount = (BigInteger) queriesReceivedList.get(i)[0];
			BigInteger respondedCount = (BigInteger) queriesRespondedList.get(i)[0];
			Timestamp date = (Timestamp) queriesRespondedList.get(i)[1];
			
			queryReceived.add(receivedCount.intValue());
			queryResponded.add(respondedCount.intValue());
			dates.add(df.format(new Date(date.getTime())));
		}
		
		faqQuery.setPrevDates(dates);
		faqQuery.setQueriesReceived(queryReceived);
		faqQuery.setQueriesResponded(queryResponded);
		return faqQuery;
	}

	private List<String> getLast15Days() {
		List<String> prevDates = new ArrayList<String>();
		LocalDate weekBeforeToday = LocalDate.now().minusDays(15);
		IntStream.rangeClosed(1, 15).mapToObj(weekBeforeToday::plusDays).forEach(ld -> {
			prevDates.add(ld.toString());
		});
		return prevDates;
	}

	@Override
	public DashboardStats getDashboardStats() throws InterruptedException, ExecutionException {

		CompletableFuture<Long> queryPendingFuture = CompletableFuture.supplyAsync(() -> {
			return faqResponseRepo.getPendingCount();
		});

		CompletableFuture<Long> userPendingFuture = CompletableFuture.supplyAsync(() -> {
			return registerRepository.getWaitingUsersCount();
		});

		CompletableFuture<Long> knowledgeCountFuture = CompletableFuture.supplyAsync(() -> {
			return knowledgeCenterRepo.count();
		});

		CompletableFuture<Long> videoCountFuture = CompletableFuture.supplyAsync(() -> {
			return videoRepo.count();
		});

		CompletableFuture<Long> notificationCountFuture = CompletableFuture.supplyAsync(() -> {
			return notificationRepository.count();
		});

		CompletionStage<DashboardStats> combinedDataCompletionStage = CompletableFuture.allOf(queryPendingFuture,
				userPendingFuture, knowledgeCountFuture, videoCountFuture, notificationCountFuture)
				.thenApply(ignoredVoid -> {
					DashboardStats stats = new DashboardStats();
					stats.setQueriesPending(queryPendingFuture.join());
					stats.setApprovalProcess(userPendingFuture.join());
					stats.setKnowledgeData(knowledgeCountFuture.join());
					stats.setVideoData(videoCountFuture.join());
					stats.setNotificationData(notificationCountFuture.join());
					return stats;
				});

		return combinedDataCompletionStage.toCompletableFuture().get();
	}

	@Override
	public void getDashboardSync() {
		faqResponseRepo.getPendingCount();
		registerRepository.getWaitingUsersCount();
		knowledgeCenterRepo.count();
		videoRepo.count();
		notificationRepository.count();
	}

	@Override
	public List<DashboardCount> getFarmerCount() {
		List<Object[]> count = farmerRepository.getFarmersByProgram();

		List<DashboardCount> farmerByProgramList = count.stream().map(dc -> {
			DashboardCount dashCount = new DashboardCount();
			dashCount.setCount(dc[0].toString());
			dashCount.setLabel(dc[1].toString());
			return dashCount;
		}).collect(Collectors.toList());

		return farmerByProgramList;
	}

	@Override
	public void updateOnlineUsers(String mobileNo) {
		UserOnline userOnline = userOnlineRepository.findByMobileNo(mobileNo);
		if (userOnline == null) {
			userOnline = new UserOnline();
			userOnline.setMobileNo(mobileNo);
		}
		userOnline.setLastUpdatedDate(new Date());
		userOnlineRepository.save(userOnline);
	}

	@Override
	public List<DashboardCount> getUserStatusCount() {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MINUTE, -2);
		Date twoMinsBack = cal.getTime();
		Long onlineUsers = userOnlineRepository.getOnlineUsers(twoMinsBack, currentDate);
		
		Long farmerCount = farmerRepository.count();
		Long approvedUsers = registerRepository.getApprovedUsersCount();
		Long usersCount = farmerCount + approvedUsers;
		
		DashboardCount totalUsersObj = new DashboardCount();
		totalUsersObj.setLabel("Total Users - " + (usersCount!= null ? usersCount.toString() : "0") );
		totalUsersObj.setCount(onlineUsers!= null ? onlineUsers.toString() : "0");
		
		DashboardCount onlineUsersObj = new DashboardCount();
		onlineUsersObj.setLabel("Online - " + (onlineUsers!= null ? onlineUsers.toString() : "0"));
		onlineUsersObj.setCount(onlineUsers!= null ? onlineUsers.toString() : "0");
		
		Long offlineUsers = usersCount - onlineUsers;
		
		DashboardCount offlineUsersObj = new DashboardCount();
		offlineUsersObj.setLabel("Offline - " + (offlineUsers!= null ? offlineUsers.toString() : "0"));
		offlineUsersObj.setCount(offlineUsers!= null ? offlineUsers.toString() : "0");
		
		List<DashboardCount> countList = new ArrayList<>();
		countList.add(onlineUsersObj);
		countList.add(offlineUsersObj);
		countList.add(totalUsersObj);

		return countList;
	}

}
