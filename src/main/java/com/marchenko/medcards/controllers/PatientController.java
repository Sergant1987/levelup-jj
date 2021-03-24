package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDate;
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
    private final AppointmentService appointmentService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService,
                             PasswordEncoder passwordEncoder, ReservationService reservationService,
                             AppointmentService appointmentService) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
        this.doctorService = doctorService;
        this.reservationService = reservationService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('PATIENT')")
    public RedirectView index(Authentication authentication) {
        String login = authentication.getName();
        Patient patient = patientService.findPatientByLogin(login);
        return new RedirectView(String.format("/patients/%d", patient.getId()));
    }

    @GetMapping("/login")
    public String login() {
        return "/patients/login";
    }

    @GetMapping("/registration")
    public String registration(
            @ModelAttribute("patientForm") PatientForm patientForm
    ) {
        return "/patients/registration";
    }

    @PostMapping("/registration")
    public RedirectView postRegistration(@Valid @ModelAttribute("patientForm") PatientForm patientForm) {
        patientForm.setPassword(passwordEncoder.encode(patientForm.getPassword()));
        Patient patient = patientService.create(patientForm);
        return new RedirectView(String.format("/patients/%d", patient.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView viewPatientMenu(@PathVariable(value = "id") Long id,
                                  Model model) {
        Patient patient = patientService.findPatientById(id);
        model.addAttribute("name", patient.getName());
        model.addAttribute("id", patient.getId());
        return new ModelAndView("/patients/patientMenu");
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

    @PostMapping("/{id}/reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public RedirectView selectDoctor(
            @PathVariable(value = "id") Long id,
            @RequestParam("doctorId") String doctorId) {
        return new RedirectView(String.format("/patients/%d/reservations/%s", id, doctorId));
    }

    @GetMapping("/{id}/reservations/{doctorId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView selectDate(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "doctorId") Long doctorId,
            @RequestParam(required = false) String dateOfReservation,
            Model model) {
        if (dateOfReservation != null&&!dateOfReservation.isBlank()) {
            model.addAttribute("dateOfReservation", dateOfReservation);
            List<Reservation> reservations =
                    reservationService.findReservationsByDoctorIdAndDate(doctorId, LocalDate.parse(dateOfReservation));
            List<TimeReservation> noReservationTime =
                    TimeReservation.findNotReservationTime(
                            reservations.stream().map(Reservation::getTime).collect(Collectors.toList()));
            model.addAttribute("noReservationTime", noReservationTime);
        }
        return new ModelAndView("/patients/selectDate");
    }

    @PostMapping("/{id}/reservations/{doctorId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public RedirectView selectTimeAndPersistReservation(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "doctorId") Long doctorId,
            @RequestParam String dateOfReservation,
            @RequestParam String time) {
        Doctor doctor = doctorService.findDoctorById(doctorId);
        Patient patient = patientService.findPatientById(id);
        reservationService.create(patient, doctor, LocalDate.parse(dateOfReservation), TimeReservation.getByValue(time));
        return new RedirectView(String.format("/patients/%d", id));
    }


    @GetMapping("/{id}/all_reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView viewAllReservations(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Reservation> reservations = reservationService.findReservationsByPatientIdWhenDateAfterNow(id);
        model.addAttribute("reservations", reservations);
        return new ModelAndView("/patients/reservationsByPatient");
    }


    //TODO приделать сортировку
    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ModelAndView viewAllAppointments(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Appointment> appointments = appointmentService.findAppointmentsByPatient(id);
        model.addAttribute("appointments", appointments);
        return new ModelAndView("/patients/appointmentsByPatient");

    }

    @ModelAttribute("specializations")
    Set<String> getSpecializations() {
        Set<String> resultWithFirstEmpty = new LinkedHashSet<>();
        resultWithFirstEmpty.add("");
        resultWithFirstEmpty.addAll(doctorService.findAllSpecialization());
        return resultWithFirstEmpty;
    }

    private Set<Doctor> findDoctors(String specialization, String doctorName) {

        if ((specialization == null || specialization.isBlank()) && (doctorName == null  || doctorName.isBlank())) {
            return doctorService.findDoctorsBySpecializationAndSurname(specialization, doctorName);
        }

        if (specialization == null || specialization.isBlank()) {
            return doctorService.findDoctorsBySurname(doctorName);
        }

        if (doctorName == null || doctorName.isBlank()) {
            return doctorService.findDoctorsBySpecialization(specialization);
        }
        return doctorService.findDoctorsBySpecializationAndSurname(specialization, doctorName);
    }



    @ModelAttribute(name = "isLoggedIn")
    public boolean isLoggedIn(){
        return SecurityContextHolder.getContext().getAuthentication()!=null;
    }


}
