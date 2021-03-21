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
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/doctors")

public class DoctorsController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ReservationService reservationService;
    private final AppointmentService appointmentService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorsController(DoctorService doctorService, PatientService patientService,
                             PasswordEncoder passwordEncoder, ReservationService reservationService,
                             AppointmentService appointmentService) {
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.reservationService = reservationService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/doctors/login";
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView index(Authentication authentication) {
        String login = authentication.getName();
        Doctor doctor = doctorService.findDoctorByLogin(login);
        return new RedirectView(String.format("/doctors/%d", doctor.getId()));
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("doctorForm") DoctorForm doctorForm) {
        return "/doctors/registration";
    }

    @PostMapping("/registration")
    public RedirectView postRegistration(@Valid @ModelAttribute("doctorForm") DoctorForm doctorForm) {
        doctorForm.setPassword(passwordEncoder.encode(doctorForm.getPassword()));
        Doctor doctor = doctorService.create(doctorForm);
        return new RedirectView(String.format("/doctors/%d", doctor.getId()));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewDoctorMenu(@PathVariable(value = "id") Long id, Model model) {
        Doctor doctor = doctorService.findDoctorById(id);
        model.addAttribute("name", doctor.getName());
        model.addAttribute("id", doctor.getId());
        return new ModelAndView("/doctors/doctorMenu");
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewSchedule(@PathVariable(value = "id") Long id,
                                     @RequestParam(required = false) String date,
                                     Model model) {
        List<Reservation> reservations = reservationService.findReservationsByDoctorIdAndDate(id, date == null ? LocalDate.now() : LocalDate.parse(date));
        model.addAttribute("reservations", reservations);
        return new ModelAndView("/doctors/schedule");
    }

    @PostMapping("/{id}/schedule")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView selectReservation(@PathVariable(value = "id") Long id,
                                          @RequestParam Long reservationId) {
        Long patientId = reservationService.findById(reservationId).getPatient().getId();
        return new RedirectView(String.format("/doctors/%d/appointments/%d", id, patientId));
    }

    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView findPatient(@PathVariable("id") Long id,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String surname,
                                    @RequestParam(required = false) String phone,
                                    Model model) {
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("phone", phone);
        model.addAttribute("patients", patientService.findPatientsByNameAndSurnameAndPhone(name, surname, phone));
        return new ModelAndView("/doctors/searchPatient");
    }

    @PostMapping("/{id}/appointments")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView selectPatient(@PathVariable("id") Long id,
                                      @RequestParam(name = "patientId") String patientId) {

        return new RedirectView(String.format("/doctors/%d/appointments/%d", id, Long.parseLong(patientId)));
    }

    @GetMapping("/{id}/appointments/{patient_id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewFormCreateAppointment(@PathVariable("id") Long id,
                                                  @PathVariable("patient_id") Long patientId,
                                                  @ModelAttribute("appointmentForm") AppointmentForm appointmentForm,
                                                  Model model) {

        return new ModelAndView("/doctors/createAppointment");
    }

    @PostMapping("/{id}/appointments/{patient_id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView createAppointment(@PathVariable("id") Long id,
                                          @PathVariable("patient_id") String patientId,
                                          @Valid @ModelAttribute("appointmentForm") AppointmentForm appointmentForm) {
        Doctor doctor = doctorService.findDoctorById(id);
        Patient patient = patientService.findPatientById(Long.parseLong(patientId));
        appointmentService.create(patient, LocalDateTime.now(), doctor, appointmentForm);
        return new RedirectView(String.format("/doctors/%d", id));
    }

}
