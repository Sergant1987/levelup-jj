package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.User;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;


@Controller
@RequestMapping("/doctors")

public class DoctorsController {

    private DoctorService doctorService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorsController(DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView index(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.findByLogin(login);
        return new RedirectView("/doctors/" + doctor.getId());
    }

    @GetMapping("/registration")
    public String registration() {
        return "/doctors/registration";
    }

    @PostMapping("/registration")
    public RedirectView postRegistration(@RequestParam String login,
                                         @RequestParam String password,
                                         @RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String dateOfBirth,
                                         @RequestParam String specialization,
                                         @RequestParam String phone,
                                         Model model) {
        Doctor doctor = new Doctor(login, passwordEncoder.encode(password), LocalDate.parse(dateOfBirth),
                name, surname, specialization, phone);
        doctorService.create(doctor);
        return new RedirectView("/doctors/" + doctor.getId());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public String viewDoctorMenu(@PathVariable(value = "id") Long id, Model model) {
        Doctor doctor = doctorService.findById(id);
        if (!hasAccessRight(doctor)) {
            return "redirect:/doctors/" + doctorService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        model.addAttribute("name", doctor.getName());
        System.out.println(doctor.getId());
        model.addAttribute("id", doctor.getId());
        return "doctorMenu";
    }

    private boolean hasAccessRight(User user) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticationUser;

        authenticationUser = doctorService.findByLogin(login);
        if (authenticationUser == null) {
            return false;
        }

        if (authenticationUser.getLogin().equals(user.getLogin())) {
            return true;
        } else {
            return false;
        }
    }


}
