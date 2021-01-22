package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping()
public class MainController {

  private DoctorService doctorService;

    @Autowired
    public MainController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping()
    public String index() {
//        Doctor doctor=new Doctor();
//        doctor.setLogin("sergant");
//
//        doctor.setPassword("$2y$12$FfkK/7PZgRX.M3MKOzwCKenlbK89rQZdmnc6hHQHFnd8PvaaKkDey");
//
//        doctor.setSpecialization("хирург");
//        doctor.setSurname("М");
//        doctor.setName("Сергей");
//        doctor.setDateOfBirth(LocalDate.now() );
//
//        doctor.setPhone("phone");
//        doctorService.create(doctor);
        return "index";
    }

}
