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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@RequestMapping("/patients")

public class PatientController {

    private PatientService patientService;
    private DoctorService doctorService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
        this.doctorService = doctorService;
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String index(Model model) {
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
        return "redirect:/patients/" + patient.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PATIENT')")
    public String patientMenu(@PathVariable(value = "id") long id, Model model) {
        Patient patient = patientService.findById(id);

        model.addAttribute("name", patient.getName());
        model.addAttribute("id", patient.getId());
        return "/patients/patientMenu";
    }

    @GetMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PATIENT')")
    public String findDoctors(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("doctorName", "");
        model.addAttribute("doctors", Collections.emptySet());
        return "/patients/selectDoctor";
    }

    @PostMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public String findDoctors(
            @PathVariable(value = "id") long id,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String doctorName,
            Model model) {
        model.addAttribute("doctorName", "");
        model.addAttribute("doctors", findDoctors(specialization, doctorName));

        return "/patients/selectDoctor";
    }

    @ModelAttribute("specializations")
    public Set<String> getSpecializations() {
        Set<String> resultWithFirstEmpty=new LinkedHashSet<>();
        resultWithFirstEmpty.add("");
        resultWithFirstEmpty.addAll(doctorService.getAllSpecialization());
        return resultWithFirstEmpty;
    }

    private Set<Doctor> findDoctors(String specialization, String doctorName) {
        if (specialization == null && doctorName == null || specialization.isBlank() && doctorName.isBlank()) {
            return doctorService.getDoctorsBySpecializationAndName(specialization, doctorName);
        }

        if (specialization == null || specialization.isBlank()) {
            return doctorService.getDoctorsBySurname(doctorName);
        }

        if (doctorName == null || doctorName.isBlank()) {
            return doctorService.getDoctorsBySpecialization(specialization);
        }
        return doctorService.getDoctorsBySpecializationAndName(specialization, doctorName);
    }


}
