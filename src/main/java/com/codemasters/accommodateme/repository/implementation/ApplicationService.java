package com.codemasters.accommodateme.repository.implementation;

import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.repository.repos.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Integer id) {
        return applicationRepository.findById(id);
    }

    public Application createApplication(Application application) {



        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByStudentId(Integer studentId) {
        return applicationRepository.findByUsersId(studentId);
    }

    public void deleteApplicationById(Integer id) {
        applicationRepository.deleteById(id);
    }
}

