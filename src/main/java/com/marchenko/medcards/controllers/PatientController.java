package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public RedirectView index(Model model) {
        //TODO security off
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        Patient patient = patientService.findByLogin(login);
        Patient patient = patientService.findPatientById(1L);
        return new RedirectView("/patients/" + patient.getId());
    }

    @GetMapping("/registration")
    public String registration(
            @ModelAttribute("patientForm")  PatientForm patientForm
    ) {
        return "/patients/registration";
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView postRegistration(
         @Valid @ModelAttribute("patientForm") PatientForm patientForm,
            Model model ) {
    //            @RequestParam String name,
//            @RequestParam String surname,
//            @RequestParam String dateOfBirth,
//            @RequestParam String login,
//            @RequestParam String password,
//            @RequestParam String phone,
//            @RequestParam String address,

//        PatientForm form = new PatientForm(login, password, name, surname, dateOfBirth, phone, address);
        System.out.println(patientForm.getLogin());
        patientForm.setPassword(passwordEncoder.encode(patientForm.getPassword()));
        Patient patient = patientService.create(patientForm);
        return new RedirectView("/patients/" + patient.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String viewPatientMenu(@PathVariable(value = "id") Long id,
                                  Model model) {
        Patient patient = patientService.findPatientById(id);
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
                    reservationService.findReservationsByDoctorAndDate(doctorId, LocalDate.parse(dateOfReservation));
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
        Doctor doctor = doctorService.findDoctorById(doctorId);
        Patient patient = patientService.findPatientById(id);
        ReservationForm reservationForm = new ReservationForm(dateOfReservation, time);
        Reservation reservation = reservationService.create(patient, doctor, reservationForm);
        return new ModelAndView(new RedirectView(String.format("/patients/%d", id)));
    }


    @GetMapping("/{id}/all_reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String viewAllReservations(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Reservation> reservations = reservationService.findReservationsByPatientAndDateAfterNow(id);
        model.addAttribute("reservations", reservations);
        return "/patients/reservationsByPatient";
    }


    //TODO приделать сортировку
    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String viewAllAppointments(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Appointment> appointments = appointmentService.findAppointmentsByPatient(id);
        model.addAttribute("appointments", appointments);
        return "/patients/appointmentsByPatient";
    }

    @ModelAttribute("specializations")
    public Set<String> getSpecializations() {
        Set<String> resultWithFirstEmpty = new LinkedHashSet<>();
        resultWithFirstEmpty.add("");
        resultWithFirstEmpty.addAll(doctorService.findAllSpecialization());
        return resultWithFirstEmpty;
    }

    private Set<Doctor> findDoctors(String specialization, String doctorName) {
        if (specialization == null && doctorName == null || specialization.isBlank() && doctorName.isBlank()) {
            return doctorService.findDoctorsBySpecializationAndName(specialization, doctorName);
        }

        if (specialization == null || specialization.isBlank()) {
            return doctorService.findDoctorsBySurname(doctorName);
        }

        if (doctorName == null || doctorName.isBlank()) {
            return doctorService.findDoctorsBySpecialization(specialization);
        }
        return doctorService.findDoctorsBySpecializationAndName(specialization, doctorName);
    }


}
