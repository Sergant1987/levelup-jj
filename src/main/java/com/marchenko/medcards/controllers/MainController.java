package com.marchenko.medcards.controllers;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        return "index";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "/contacts/contacts";
    }

    @GetMapping("/about")
    public String about(Model model) {
        List<Doctor> doctors = doctorService.findAllDoctors();
        model.addAttribute("doctors",doctors);
        return "/about/about";
    }

    @ModelAttribute(name = "isLoggedIn")
    public boolean isLoggedIn(){
        return SecurityContextHolder.getContext().getAuthentication()!=null;
    }
}
