package com.codemasters.accommodateme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codemasters.accommodateme.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByArea(String area);

    Optional<Location> findByStreetName(String streetName);
}