package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.service.TestEntityGenerator;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService patientService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    private TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    @Test
    public void index() throws Exception {
        Patient patientExpect = testEntityGenerator.getPatients().get(0);
        patientExpect.setId(1L);

        Mockito.when(patientService.findPatientByLogin(patientExpect.getLogin())).thenReturn(patientExpect);

        mvc.perform(MockMvcRequestBuilders.get("/patients/")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patientExpect.getLogin())
                        .password(patientExpect.getPassword())
                        .authorities(patientExpect.getRole().getAuthorities())
                ))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d", patientExpect.getId())));
        Mockito.verify(patientService, Mockito.times(1)).findPatientByLogin(Mockito.anyString());
    }

    @Test
    public void login() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/patients/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/patients/login"));
    }

    @Test
    public void registration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/patients/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/patients/registration"));
    }

    @Test
    public void postRegistration() throws Exception {
        Patient patientExpect = testEntityGenerator.getPatients().get(0);
        patientExpect.setId(1L);

        Mockito.when(patientService.create(patientExpect.getForm())).thenReturn(patientExpect);

        mvc.perform(MockMvcRequestBuilders.post("/patients/registration")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("patientForm", patientExpect.getForm()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d", patientExpect.getId())));
        Mockito.verify(patientService, Mockito.times(1)).create(patientExpect.getForm());
    }

    @Test
    public void viewPatientMenu() throws Exception {
        Patient patientExpect = testEntityGenerator.getPatients().get(0);
        patientExpect.setId(1L);

        Mockito.when(patientService.findPatientById(patientExpect.getId())).thenReturn(patientExpect);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patientExpect.getLogin())
                        .password(patientExpect.getPassword())
                        .authorities(patientExpect.getRole().getAuthorities()))
                .param("name", patientExpect.getName())
                .param("id", patientExpect.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/patients/patientMenu"));
        Mockito.verify(patientService, Mockito.times(1)).findPatientById(Mockito.anyLong());
    }

    @Test
    public void findDoctors() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Set<Doctor> foundDoctors = new HashSet<>(testEntityGenerator.getDoctors());
        Doctor findingDoctor = testEntityGenerator.getDoctors().get(0);
        Mockito.when(doctorService.findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization", findingDoctor.getSpecialization())
                .param("doctorSurname", findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctorSurname", findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization", "")
                .param("doctorSurname", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctorSurname", ""))
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService, Mockito.times(2)).findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString());

        Mockito.when(doctorService.findDoctorsBySurname(Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization", "")
                .param("doctorSurname", findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctorSurname", findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorsBySurname(Mockito.anyString());

        Mockito.when(doctorService.findDoctorsBySpecialization(Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization", findingDoctor.getSpecialization())
                .param("doctorSurname", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctorSurname", ""))
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorsBySpecialization(Mockito.anyString());

    }

    @Test
    public void selectDoctor() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);

        mvc.perform(MockMvcRequestBuilders.post("/patients/{id}/reservations", patient.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("doctorId", doctor.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d/reservations/%s", patient.getId(), doctor.getId())));
    }

    @Test
    public void selectDate() throws Exception{
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations/{doctorId}",patient.getId(),doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDate"));


        List<Reservation> reservations=testEntityGenerator.getReservations();

        List<TimeReservation> noReservationTime =
                TimeReservation.findNotReservationTime(
                        reservations.stream().map(Reservation::getTime).collect(Collectors.toList()));

        Mockito.when(reservationService.findReservationsByDoctorIdAndDate(doctor.getId(),reservations.get(0).getDate())).thenReturn(reservations);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations/{doctorId}",patient.getId(),doctor.getId())
        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
        .user(patient.getLogin())
        .password(patient.getPassword())
        .authorities(patient.getRole().getAuthorities()))
        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
        .param("dateOfReservation",reservations.get(0).getDate().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("noReservationTime", noReservationTime))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDate"));

        Mockito.verify(reservationService,Mockito.times(1))
                .findReservationsByDoctorIdAndDate(doctor.getId(),reservations.get(0).getDate());

    }

    @Test
    public void selectTimeAndPersistReservation() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);

        Reservation reservation = testEntityGenerator.getReservations().get(0);

        Mockito.when(patientService.findPatientById(Mockito.anyLong())).thenReturn(patient);
        Mockito.when(doctorService.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
        Mockito.when(reservationService.create(patient, doctor, reservation.getDate(), reservation.getTime())).thenReturn(reservation);

        mvc.perform(MockMvcRequestBuilders.post("/patients/{id}/reservations/{doctorId}", patient.getId(), doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("dateOfReservation", reservation.getDate().toString())
                .param("time", reservation.getTime().toString())
        )
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d", patient.getId())));
        Mockito.verify(patientService, Mockito.times(1)).findPatientById(Mockito.anyLong());
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorById(Mockito.anyLong());
        Mockito.verify(reservationService, Mockito.times(1)).create(patient, doctor, reservation.getDate(), reservation.getTime());
    }


    @Test
    public void viewAllReservations() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        List<Reservation> reservations = testEntityGenerator.getReservations();

        Mockito.when(reservationService.findReservationsByPatientIdWhenDateAfterNow(Mockito.anyLong()))
                .thenReturn(reservations);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/all_reservations", patient.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("reservations", reservations))
                .andExpect(MockMvcResultMatchers.view().name("/patients/reservationsByPatient"));
        Mockito.verify(reservationService, Mockito.times(1))
                .findReservationsByPatientIdWhenDateAfterNow(Mockito.anyLong());
    }


    @GetMapping("/{id}/all_reservations")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String viewAllReservations(
            @PathVariable(value = "id") Long id,
            Model model) {
        List<Reservation> reservations = reservationService.findReservationsByPatientIdWhenDateAfterNow(id);
        model.addAttribute("reservations", reservations);
        return "/patients/reservationsByPatient";
    }


    @Test
    public void viewAllAppointments() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        List<Appointment> appointmentsExpect = testEntityGenerator.getAppointments();

        Mockito.when(appointmentService.findAppointmentsByPatient(Mockito.anyLong())).thenReturn(appointmentsExpect);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/appointments", patient.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("appointments", appointmentsExpect))
                .andExpect(MockMvcResultMatchers.view().name("/patients/appointmentsByPatient"));
        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentsByPatient(Mockito.anyLong());
    }

    @Autowired
    private PatientController patientController;

    @Test
    public void getSpecializations() {
        Set<String> specializationsExpect = new HashSet<>(Arrays.asList("хирург", "терапевт"));
        specializationsExpect.add("");
        Mockito.when(doctorService.findAllSpecialization()).thenReturn(specializationsExpect);
        Set<String> specializationsActual = patientController.getSpecializations();
        assertEquals(specializationsExpect, specializationsActual);

    }

}

