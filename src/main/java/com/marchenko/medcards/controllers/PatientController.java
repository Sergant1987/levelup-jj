package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.models.TimeReservation;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patients")

public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ReservationService reservationService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService, PasswordEncoder passwordEncoder, ReservationService reservationService) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
        this.doctorService = doctorService;
        this.reservationService = reservationService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('PATIENT')")
    public RedirectView index(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientService.findByLogin(login);
        return new RedirectView("/patients/" + patient.getId());
    }

    @GetMapping("/registration")
    public String registration() {
        return "/patients/registration";
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView postRegistration(
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
        return new RedirectView("/patients/" + patient.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String viewPatientMenu(@PathVariable(value = "id") Long id,
                                  Model model) {
        Patient patient = patientService.findById(id);
        model.addAttribute("name", patient.getName());
        model.addAttribute("id", patient.getId());
        return "/patients/patientMenu";
    }

    @PostMapping("/{id}/reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView selectDoctor(
            @PathVariable(value = "id") Long id,
            @RequestParam Long doctorId,
            Model model) {
        String redirect = String.format("/patients/%d/reservations/%d", id, doctorId);
        return new ModelAndView(new RedirectView(redirect));
    }

    @GetMapping("/{id}/reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String findDoctors(
            @PathVariable(value = "id") Long id,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String doctorSurname,
            Model model) {
        model.addAttribute("doctorSurname", doctorSurname);
        model.addAttribute("doctors", findDoctors(specialization, doctorSurname));
        return "/patients/selectDoctor";
    }


    @GetMapping("/{id}/reservations/{doctorId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String selectDate(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "doctorId") Long doctorId,
            @RequestParam(required = false) String dateOfReservation,
            Model model) {
        if (dateOfReservation != null) {
            model.addAttribute("dateOfReservation", dateOfReservation);
            List<Reservation> reservations =
                    reservationService.findByDoctorAndDate(doctorId, LocalDate.parse(dateOfReservation));
            List<TimeReservation> noReservationTime =
                    TimeReservation.findNotReservationTime(
                            reservations.stream().map(Reservation::getTime).collect(Collectors.toList()));
            model.addAttribute("noReservationTime", noReservationTime);
        }
        return "/patients/selectDate";
    }

    @PostMapping("/{id}/reservations/{doctorId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView selectTimeAndPersistReservation(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "doctorId") Long doctorId,
            @RequestParam String dateOfReservation,
            @RequestParam String time,
            Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(id);
        Reservation reservation = reservationService.create(patient, doctor, LocalDate.parse(dateOfReservation), TimeReservation.getByValue(time));
        System.out.println(reservation);
        return new ModelAndView(new RedirectView(String.format("/patients/%d", id)));
    }


    @GetMapping("/{id}/all_reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String s(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Reservation> reservations = reservationService.findByPatientAndDateAfterNow(id);
        model.addAttribute("reservations", reservations);
        return "/patients/reservationsByPatient";
    }

    @ModelAttribute("specializations")
    public Set<String> getSpecializations() {
        Set<String> resultWithFirstEmpty = new LinkedHashSet<>();
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
