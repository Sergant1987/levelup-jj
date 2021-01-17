package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("asd");
        Doctor doctor=new Doctor();
        doctor.setPassword("!");
        doctor.setLogin("2");
        doctor.setName("3");
        doctor.setSurname("4");
        doctor.setDateOfBirth(LocalDate.now());
        doctor.setSpecialization("5");
        doctor.setPhone("+6");
        doctorService.create(doctor);
        return "index";
    }

}
