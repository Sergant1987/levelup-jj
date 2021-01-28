package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth/")
public class AuthController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public AuthController(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "/auth/registration";
    }

    @GetMapping("/success")
    public String successPage() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Doctor doctor = doctorService.findByLogin(login);
            return "redirect:/doctors/";
        }catch (UsernameNotFoundException e){}
            try {
            Patient patient = patientService.findByLogin(login);
            return "redirect:/patients/";
        }catch (UsernameNotFoundException e){}

        return "redirect:/";
    }


}
