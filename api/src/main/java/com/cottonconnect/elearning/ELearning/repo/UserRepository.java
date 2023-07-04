package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByMobileNo(String mobileNo);

	@Query(value = "FROM User u where u.userId=:userName and u.password=:password")
	public Optional<User> checkLogin(String userName, String password);
	
	List<User> findByIdIn(List<Long> ids);
}
