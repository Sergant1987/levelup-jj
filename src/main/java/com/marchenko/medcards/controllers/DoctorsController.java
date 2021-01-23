package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/doctors")
@PreAuthorize("hasAuthority('DOCTOR')")
public class DoctorsController {

    private DoctorService doctorService;

    @Autowired
    public DoctorsController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }



    @GetMapping("")

    public String login( Model model) {
//        Doctor doctor = doctorService.findByLogin(login);

//        model.addAttribute("name", doctor.getName());
        System.out.println("++++++++++++++");
        return "/doctors/info";
    }

    @GetMapping("/registration")
    public String registration() {

        return "/doctors/registration";
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public String postRegistration(@RequestParam String login,
                                   @RequestParam String password,
                                   @RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String dateOfBirth,
                                   @RequestParam String specialization,
                                   @RequestParam String phone,
                                   Model model) {

        Doctor doctor = new Doctor(login, password, LocalDate.parse(dateOfBirth),
                name, surname, specialization, phone);
        doctorService.create(doctor);
        return info(doctor.getId(), model);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String info(@PathVariable(value = "id") long id, Model model) {
        Doctor doctor = doctorService.findById(id);
        System.out.println(doctor);
        model.addAttribute("name", doctor.getName());
        return "/doctors/info";
    }

}
