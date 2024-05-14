package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.LocationDTO;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/save/{resId}")
    public ResponseEntity<Location> addLocation(@RequestBody Location location, @PathVariable Long resId) {
        Location newLocation = locationService.addLocation(location, resId);
        return ResponseEntity.ok(newLocation);
    }

    @GetMapping("/all")
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Optional<LocationDTO>> getLocationById(@PathVariable Long id) {
        try {
            Optional<LocationDTO> location = locationService.getLocationById(id);
            return ResponseEntity.ok(location);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByArea")
    public ResponseEntity<List<LocationDTO>> findLocationByArea(@RequestParam String area) {
        List<LocationDTO> locations = locationService.getLocationByArea(area);
        if (locations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/findByStreetName")
    public ResponseEntity<List<LocationDTO>> findLocationByStreetName(@RequestParam String streetName) {
        List<LocationDTO> locations = locationService.getLocationByStreetName(streetName);
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }

    @PutMapping("/update/{id}/resId/{resId}")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location, @PathVariable Long id, @PathVariable Long resId) {
        try {
            Location updatedLocation = locationService.updateLocation(location, id, resId);
            return ResponseEntity.ok(updatedLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/findbyCity")
    public ResponseEntity<List<LocationDTO>> findLocationByCity(@RequestParam String city) {
        List<LocationDTO> locations = locationService.getLocationByCity(city);
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/findByProvince")
    public ResponseEntity<List<LocationDTO>> findLocationByProvince(@RequestParam String province) {
        List<LocationDTO> locations = locationService.getLocationByStreetName(province);
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }

}
