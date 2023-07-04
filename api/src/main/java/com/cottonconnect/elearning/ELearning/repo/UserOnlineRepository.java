package com.cottonconnect.elearning.ELearning.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.UserOnline;

@Repository
public interface UserOnlineRepository extends JpaRepository<UserOnline, Long>{
	UserOnline findByMobileNo(String mobileNo);
	
	@Query(value = "SELECT COUNT(u.id) FROM user_online u where u.last_updated_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	Long getOnlineUsers(Date startDate, Date endDate);
}
