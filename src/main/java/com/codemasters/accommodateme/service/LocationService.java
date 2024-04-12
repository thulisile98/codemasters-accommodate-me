package com.codemasters.accommodateme.service;

import java.util.List;
import java.util.Optional;

import com.codemasters.accommodateme.entity.Location;

public interface LocationService {

    String addLocation(Location location, Long resId);

    List<Location> getAllLocations();

    Optional<Location> getLocationById(Integer id);

    Optional<Location> getLocationByResId(Integer resId);

    Optional<Location> getLocationByStreetName(String streetName);

    Optional<Location> getLocationByArea(String area);

    Location updateLocation(Location location, Integer id, Long resId);

    void deleteLocation(Integer id);
}
