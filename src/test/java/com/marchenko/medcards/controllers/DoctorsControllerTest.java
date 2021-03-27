package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorsControllerTest {
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

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    @Test
    public void testDoctorIndexPage() throws Exception {
        Doctor doctorExpect = testEntityGenerator.getDoctors().get(0);
        doctorExpect.setId(1L);

        Mockito.when(doctorService.findDoctorByLogin(Mockito.anyString())).thenReturn(doctorExpect);

        mvc.perform(MockMvcRequestBuilders.get("/doctors/")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(doctorExpect.getLogin())
                        .password(doctorExpect.getPassword())
                        .authorities(doctorExpect.getRole().getAuthorities())
                ))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/doctors/%d", doctorExpect.getId())));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorByLogin(Mockito.anyString());
    }

    @Test
    public void testDoctorLoginPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/doctors/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/login"));
    }

    @Test
    public void testDoctorRegistration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/doctors/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/registration"));
    }

    @Test
    public void testDoctorPostRegistration() throws Exception {
        Doctor doctorExpect = testEntityGenerator.getDoctors().get(0);
        doctorExpect.setId(1L);

        Mockito.when(doctorService.create(doctorExpect.getForm())).thenReturn(doctorExpect);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/registration")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("doctorForm", doctorExpect.getForm()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/doctors/%d", doctorExpect.getId())));
        Mockito.verify(doctorService, Mockito.times(1)).create(doctorExpect.getForm());
    }

    @Test
    public void testViewDoctorMenu() throws Exception {
        Doctor doctorExpect = testEntityGenerator.getDoctors().get(0);
        doctorExpect.setId(1L);

        Mockito.when(doctorService.findDoctorById(Mockito.anyLong())).thenReturn(doctorExpect);

        mvc.perform(MockMvcRequestBuilders.get("/doctors/{id}", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(doctorExpect.getLogin())
                        .password(doctorExpect.getPassword())
                        .authorities(doctorExpect.getRole().getAuthorities()))
                .param("name", doctorExpect.getName())
                .param("id", doctorExpect.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/doctorMenu"));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorById(doctorExpect.getId());
    }

    @Test
    public void testViewSchedule() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);

        List<Reservation> reservations = testEntityGenerator.getReservations();

        Mockito.when(reservationService.findReservationsByDoctorIdAndDate(doctor.getId(), reservations.get(0).getDate())).thenReturn(reservations);

        mvc.perform(MockMvcRequestBuilders.get("/doctors/{id}/schedule", doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("date", reservations.get(0).getDate().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("reservations", reservations))
                .andExpect(MockMvcResultMatchers.view().name("/doctors/schedule"));
        Mockito.verify(reservationService, Mockito.times(1)).findReservationsByDoctorIdAndDate(doctor.getId(), reservations.get(0).getDate());
    }

    @Test
    public void testSelectReservation() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);

        Patient patient = testEntityGenerator.getPatients().get(0);

        Reservation reservation = testEntityGenerator.getReservations().get(0);
        reservation.setId(1L);

        Mockito.when(reservationService.findById(reservation.getId())).thenReturn(reservation);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/{id}/schedule", doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("reservationId", reservation.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/doctors/%d/appointments/%d", doctor.getId(), patient.getId())));
        Mockito.verify(reservationService, Mockito.times(1)).findById(reservation.getId());
    }

    @Test
    public void testFindPatient() throws Exception {
        Doctor doctorExpect = testEntityGenerator.getDoctors().get(0);
        doctorExpect.setId(1L);

        Mockito.when(
                patientService.findPatientsByNameAndSurnameAndPhone(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(testEntityGenerator.getPatients());
        Patient findingPatient = testEntityGenerator.getPatients().get(0);

        mvc.perform(MockMvcRequestBuilders.get("/doctors/{id}/appointments", doctorExpect.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(doctorExpect.getLogin())
                        .password(doctorExpect.getPassword())
                        .authorities(doctorExpect.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", findingPatient.getName())
                .param("surname", findingPatient.getSurname())
                .param("phone", findingPatient.getPhone()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/searchPatient"));
        Mockito.verify(patientService, Mockito.times(1))
                .findPatientsByNameAndSurnameAndPhone(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testSelectPatient() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);
        Patient foundPatient = testEntityGenerator.getPatients().get(0);
        foundPatient.setId(1L);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/{id}/appointments", doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("patientId", foundPatient.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        String.format("/doctors/%d/appointments/%d", doctor.getId(), foundPatient.getId())));
    }

    @Test
    public void testViewFormCreateAppointment() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Appointment appointment = testEntityGenerator.getAppointments().get(0);

        mvc.perform(MockMvcRequestBuilders.get("/doctors/{id}/appointments/{patient_id}", doctor.getId(), patient.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("appointmentForm", new AppointmentForm(appointment.getDiagnosis(), appointment.getDataAppointment())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/createAppointment"));
    }

    @Test
    public void testCreateAppointment() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Appointment appointment = testEntityGenerator.getAppointments().get(0);

        Mockito.when(doctorService.findDoctorById(doctor.getId())).thenReturn(doctor);
        Mockito.when(patientService.findPatientById(patient.getId())).thenReturn(patient);
        Mockito.when(appointmentService.create(patient, appointment.getAppointmentId().getDateOfAppointment(), doctor,
                new AppointmentForm(appointment.getDiagnosis(), appointment.getDataAppointment()))).thenReturn(appointment);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/{id}/appointments/{patient_id}", doctor.getId(), patient.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                        .user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("appointmentForm", new AppointmentForm(appointment.getDiagnosis(), appointment.getDataAppointment())))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/doctors/%d", doctor.getId())));
        Mockito.verify(doctorService, Mockito.times(1)).findDoctorById(doctor.getId());
        Mockito.verify(patientService, Mockito.times(1)).findPatientById(patient.getId());
    }

}