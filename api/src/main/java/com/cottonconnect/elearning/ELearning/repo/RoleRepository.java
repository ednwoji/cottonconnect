package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottonconnect.elearning.ELearning.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
