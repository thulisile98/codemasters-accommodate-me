package com.codemasters.accommodateme.repository;

import com.codemasters.accommodateme.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
