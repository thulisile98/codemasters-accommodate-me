package com.codemasters.accommodateme.repository.services;

import com.codemasters.accommodateme.dto.LocationDTO;
import com.codemasters.accommodateme.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location addLocation(Location location, Long resId);

    List<LocationDTO> getAllLocations();

    Optional<LocationDTO> getLocationById(Long id);

    List<LocationDTO> getLocationByStreetName(String streetName);

    List<LocationDTO> getLocationByArea(String area);

    Location updateLocation(Location location, Long id, Long resId);

    List<LocationDTO> getLocationByCity(String city);

}
