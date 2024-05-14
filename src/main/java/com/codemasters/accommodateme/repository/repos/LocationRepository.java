package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

    List<Location> findByArea(String area);

    List<Location> findByStreetName(String streetName);

    List<Location> findByCity(String city);
}
