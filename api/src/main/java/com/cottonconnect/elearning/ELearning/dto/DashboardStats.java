package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStats {
	private Long queriesPending;
	private Long approvalProcess;
	private Long knowledgeData;
	private Long videoData;
	private Long notificationData;
	private Long escalationData;

}
