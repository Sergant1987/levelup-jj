package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.User;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("/doctors")

public class DoctorsController {

    private DoctorService doctorService;

    @Autowired
    public DoctorsController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    @PreAuthorize("hasAuthority('DOCTOR')")
//    @PostAuthorize("#username")
    public String main(Model model) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.findByLogin(login);
        //        Doctor doctor = doctorService.findByLogin(login);

//        model.addAttribute("name", doctor.getName());

        return "redirect:/doctors/" + doctor.getId();
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

        Doctor doctor = new Doctor(login, passwordEncoder.encode(password), LocalDate.parse(dateOfBirth),
                name, surname, specialization, phone);
        doctorService.create(doctor);
        return "redirect:/doctors/" + doctor.getId();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('DOCTOR')")
    public String info(@PathVariable(value = "id") long id, Model model) {
        Doctor doctor=null;
        try {
            doctor = doctorService.findById(id);
            if (!hasAccessRight(doctor)) {
                return "redirect:/doctors/"+ doctorService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            }
        } catch (NoSuchElementException e) {
          return "redirect:/doctors/"+ doctorService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        }


        model.addAttribute("name", doctor.getName());
        return "/doctors/info";
    }

    private boolean hasAccessRight(User user) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticationUser;
        try {
            authenticationUser = doctorService.findByLogin(login);
        } catch (NoSuchElementException e) {
            return false;
        }

        if (authenticationUser.getLogin().equals(user.getLogin())) {
            return true;
        } else {
            return false;
        }
    }



}
