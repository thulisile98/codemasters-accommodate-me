package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.repository.implementation.ApplicationService;
import com.codemasters.accommodateme.service.OurUserDetailsService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final OurUserDetailsService studentService;


    @Autowired
    public ApplicationController(ApplicationService applicationService, OurUserDetailsService studentService) {
        this.applicationService = applicationService;
        this.studentService = studentService;
    }

    @PostMapping("/create/{studentId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Application> createApplication(@PathVariable("studentId") Integer studentId, @RequestBody Application application) {


        Optional<User> studentOptional = studentService.findById(studentId);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        application.setUsers(studentOptional.get());


        Application createdApplication = applicationService.createApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }


    @GetMapping("/get/{studentId}")
    @RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<String> getStudentApplications(@PathVariable("studentId") Integer studentId) {

/*
        List<Application> applications = studentService.getApplicationsForStudent(studentId);
        return ResponseEntity.ok(applications);*/

        return ResponseEntity.ok("GET: getStudentApplications ");



    }


    @GetMapping("/getAll")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<Application>> getAllApplications() {
 List<Application> applications = applicationService.getAllApplications();
      return new ResponseEntity<>(applications, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteApplicationById(@PathVariable("id") Integer id) {

        return ResponseEntity.ok("DELETE: delete Application ");

        /*applicationService.deleteApplicationById(id);*/
    }


//get student with id
//    @GetMapping("/admin/student/{id}")
//    public Optional<Users> getStudentbyId(@PathVariable Integer id){
//        return studentService.findById(id);
//    }


    // Get all applications for a certain student using their ID
//    @GetMapping("/admin/applications/{studentId}")
//    public ResponseEntity<List<Application>> getApplicationsByStudentId(@PathVariable Integer studentId) {
//        List<Application> applications = applicationService.getApplicationsByStudentId(studentId);
//        return ResponseEntity.ok(applications);
//    }




}