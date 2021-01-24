package com.marchenko.medcards.controllers;


import com.marchenko.medcards.models.Patient;

import com.marchenko.medcards.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.time.LocalDate;

@Controller
@RequestMapping()
public class MainController {

  private PatientService patientService;

    @Autowired
    public MainController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public String index() {
//        Patient patient=new Patient();
//        patient.setLogin("serg");
//        patient.setPassword("$2y$12$FfkK/7PZgRX.M3MKOzwCKenlbK89rQZdmnc6hHQHFnd8PvaaKkDey");
//        patient.setSurname("М");
//        patient.setName("Сергей");
//        patient.setDateOfBirth(LocalDate.now() );
//        patient.setPhone("phone");
//        patient.setAddress("address");
//
//        patientService.create(patient);
        return "index";
    }

}
