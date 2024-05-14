package com.codemasters.accommodateme.repository.implementation;

import com.codemasters.accommodateme.dto.LocationDTO;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.repository.repos.LocationRepository;
import com.codemasters.accommodateme.repository.services.LocationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ResidenceRepository residenceRepository;

    private final EntityManager entityManager;

    @Override
    public Location addLocation(Location location, Long resId) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence with ID " + resId + " does not exist"));
        location.setResidence(residence);
        return locationRepository.save(location);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> location = locationRepository.findAll();
        return location.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LocationDTO> getLocationById(Long id) {
        return locationRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<LocationDTO> getLocationByStreetName(String streetName) {
        List<Location> locations = locationRepository.findByStreetName(streetName);
        return locations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getLocationByArea(String area) {
        List<Location> locations = locationRepository.findByArea(area);
        return locations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Location updateLocation(LocationDTO locationDTO, Long id, Long resId) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));

        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        existingLocation.setResidence(residence);
        existingLocation.setArea(locationDTO.getArea());
        existingLocation.setCity(locationDTO.getCity());
        existingLocation.setProvince(locationDTO.getProvince());
        existingLocation.setStreetName(locationDTO.getStreetName());
        existingLocation.setZipCode(locationDTO.getZipCode());
        existingLocation.setStreetNumber(locationDTO.getStreetNumber());

        return locationRepository.save(existingLocation);
    }

    @Override
    public List<LocationDTO> getLocationByCity(String city) {
        List<Location> locations = locationRepository.findByCity(city);
        return locations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LocationDTO convertToDTO(Location location) {
        return new LocationDTO(
                location.getLocationId(),
                location.getCity(),
                location.getProvince(),
                location.getArea(),
                location.getStreetName(),
                location.getZipCode(),
                (location.getResidence() != null) ? location.getResidence().getName() : null,
                location.getStreetNumber()
        );
    }

}
