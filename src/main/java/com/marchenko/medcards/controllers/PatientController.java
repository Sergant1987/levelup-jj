package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/patients")

public class PatientController {

    private PatientService patientService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientService patientService, PasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String main(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientService.findByLogin(login);

        return "redirect:/patients/" + patient.getId();

    }

    @GetMapping("/registration")
    public String registration() {

        return "/patients/registration";
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public String postRegistration(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String dateOfBirth,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {
        Patient patient =
                new Patient(login, passwordEncoder.encode(password),
                        name, surname, LocalDate.parse(dateOfBirth),
                        phone, address);

        patientService.create(patient);
        return "redirect:/doctors/" + patient.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PATIENT')")
    public String info(@PathVariable(value = "id") long id, Model model) {
        Patient patient = patientService.findById(id);

        model.addAttribute("name", patient.getName());
        model.addAttribute("id", patient.getId());
        return "/patients/info";
    }


}
