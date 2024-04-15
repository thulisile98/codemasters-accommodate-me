package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Integer id);
}
