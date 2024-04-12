package com.codemasters.accommodateme.service.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.repository.LocationRepository;
import com.codemasters.accommodateme.repository.ResidenceRepository;
import com.codemasters.accommodateme.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ResidenceRepository residenceRepository;

    public LocationServiceImpl(LocationRepository locationRepository, ResidenceRepository residenceRepository) {
        this.locationRepository = locationRepository;
        this.residenceRepository = residenceRepository;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location updateLocation(Location location, Integer id, Long resId) {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isPresent()) {

            Location existingLocation = foundLocation.get();

            if (!existingLocation.getResidence().getResidenceId().equals(resId)) {
                throw new RuntimeException("Unauthorized to update this location");
            }

            existingLocation.setCity(location.getCity());
            existingLocation.setProvince(location.getProvince());
            existingLocation.setArea(location.getArea());
            existingLocation.setResidence(location.getResidence());
            existingLocation.setStreetName(location.getStreetName());
            existingLocation.setStreetNumber(location.getStreetNumber());
            existingLocation.setZipCode(location.getZipCode());

            return locationRepository.save(existingLocation);

        } else {

            throw new RuntimeException("Location with ID " + id + " not found");
        }
    }

    @Override
    public void deleteLocation(Integer locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public String addLocation(Location location, Long resId) {
        Optional<Residence> resOptional = residenceRepository.findById(resId);

        if (resOptional.isPresent()) {
            Residence residence = resOptional.get();

            location.setResidence(residence);

            locationRepository.save(location);

            return "Location successfully saved";
        } else {
            return "Error saving room: Residence with ID " + resId + " does not exist";
        }
    }

    @Override
    public Optional<Location> getLocationById(Integer id) {

        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isPresent()) {
            return locationRepository.findById(id);
        } else {
            throw new RuntimeException("Location with " + id + "not found");
        }

    }

    @Override
    public Optional<Location> getLocationByArea(String area) {
        Optional<Location> foundLocation = locationRepository.findByArea(area);

        if (foundLocation.isPresent()) {
            return locationRepository.findByArea(area);
        } else {
            throw new RuntimeException("Location with area name " + area + " not found");
        }
    }

    @Override
    public Optional<Location> getLocationByResId(Integer resId) {
        Optional<Location> foundLocation = locationRepository.findById(resId);

        if (foundLocation.isPresent()) {
            return locationRepository.findById(resId);
        } else {
            throw new RuntimeException("Location with residence ID " + resId + "not found");
        }
    }

    @Override
    public Optional<Location> getLocationByStreetName(String streetName) {
        Optional<Location> foundLocation = locationRepository.findByStreetName(streetName);

        if (foundLocation.isPresent()) {
            return locationRepository.findByArea(streetName);
        } else {
            throw new RuntimeException("Location with street name " + streetName + " not found");
        }
    }

}
