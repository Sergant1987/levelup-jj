package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/doctors")

public class DoctorsController {

    private DoctorService doctorService;
    private final PatientService patientService;
    private final ReservationService reservationService;
    private final AppointmentService appointmentService;

    private PasswordEncoder passwordEncoder;

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


    @GetMapping("")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public RedirectView index(Model model) {
        //TODO security off
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        Doctor doctor = doctorService.findByLogin(login);
        Doctor doctor = doctorService.findDoctorById(17L);
        return new RedirectView(String.format("/doctors/%d", doctor.getId()));
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
        DoctorForm form = new DoctorForm(login, password, name, surname, LocalDate.parse(dateOfBirth), phone, specialization);
        Doctor doctor=doctorService.create(form);
        return new RedirectView(String.format("/doctors/%d", doctor.getId()));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewDoctorMenu(@PathVariable(value = "id") Long id, Model model) {
        Doctor doctor = doctorService.findDoctorById(id);
        if (!hasAccessRight(doctor)) {
            return new ModelAndView(
                    new RedirectView(
                            String.format("/doctors/%d", doctorService.findDoctorByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId())));
        }
        model.addAttribute("name", doctor.getName());
        System.out.println(doctor.getId());
        model.addAttribute("id", doctor.getId());
        return new ModelAndView("doctors/doctorMenu");
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewSchedule(@PathVariable(value = "id") Long id,
                                     @RequestParam(required = false) String date,
                                     Model model) {
        List<Reservation> reservations = reservationService.findByDoctorAndDate(id, date == null ? LocalDate.now() : LocalDate.parse(date));
        model.addAttribute("reservations", reservations);
        return new ModelAndView("doctors/schedule");
    }

    @PostMapping("/{id}/schedule")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView selectReservation(@PathVariable(value = "id") Long id,
                                          @RequestParam Long reservationId,
                                          Model model) {
        Long patientId = reservationService.findById(reservationId).getPatient().getId();
        return new ModelAndView(new RedirectView(String.format("/doctors/%d/appointments/%d", id, patientId)));
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
    public ModelAndView selectPatient(@PathVariable("id") Long id,
                                      @RequestParam Long patientId,
                                      Model model) {
        return new ModelAndView(new RedirectView(String.format("/doctors/%d/appointments/%d", id, patientId)));
    }

    @GetMapping("/{id}/appointments/{patient_id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView viewFormCreateAppointment(@PathVariable("id") Long id,
                                                  @PathVariable("patient_id") Long patientId,
                                                  Model model) {
        model.addAttribute("diagnosis", "");
        model.addAttribute("data", "");
        return new ModelAndView("/doctors/createAppointment");
    }

    @PostMapping("/{id}/appointments/{patient_id}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ModelAndView createAppointment(@PathVariable("id") Long id,
                                          @PathVariable("patient_id") Long patientId,
                                          @RequestParam String diagnosis,
                                          @RequestParam String data,
                                          Model model) {
        Doctor doctor = doctorService.findDoctorById(id);
        Patient patient = patientService.findPatientById(patientId);
        AppointmentForm form = new AppointmentForm(diagnosis, data);
        Appointment appointment=appointmentService.create(patient,LocalDateTime.now() ,doctor,form);
        return new ModelAndView(new RedirectView(String.format("/doctors/%d", id)));

    }

    private boolean hasAccessRight(User user) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticationUser;

        authenticationUser = doctorService.findDoctorByLogin(login);
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
