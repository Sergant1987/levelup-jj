package com.marchenko.medcards.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")

public class PatientController {


    @GetMapping("")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String main( Model model) {
//        Doctor doctor = doctorService.findByLogin(login);

//        model.addAttribute("name", doctor.getName());

        return "/patients/info";
    }

}
