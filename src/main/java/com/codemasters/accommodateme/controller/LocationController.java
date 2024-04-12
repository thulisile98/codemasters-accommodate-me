package com.codemasters.accommodateme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.service.LocationService;

@RestController
@RequestMapping("api/vi/locations")
public class LocationController {

    public final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/save/{resId}")
    public String addLocation(@RequestBody Location room, @PathVariable Long resId) {
        return locationService.addLocation(room, resId);
    }

    @GetMapping("/all")
    public List<Location> getAllLocations() {

        return locationService.getAllLocations();
    }

    @GetMapping("/findById/{id}")
    public Optional<Location> getLocationById(@PathVariable Integer id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/findByResId/{resId}")
    public Optional<Location> getLocationByResId(@PathVariable Integer resId) {
        return locationService.getLocationByResId(resId);
    }

    @GetMapping("/findByArea/{area}")
    public Optional<Location> findByArea(@PathVariable String area) {

        return locationService.getLocationByArea(area);
    }

    @GetMapping("/findByStreetName/{streetName}")
    public Optional<Location> getLocationByStreetName(@PathVariable String streetName) {

        return locationService.getLocationByStreetName(streetName);
    }

    @PutMapping("/update/{id}/resId/{resId}")
    public Location updateLocation(@RequestBody Location location, @PathVariable Integer id, @PathVariable Long resId) {

        try {

            return locationService.updateLocation(location, id, resId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while updating location", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteLocation(id);
    }

}
