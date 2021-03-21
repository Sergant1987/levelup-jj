package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Role;
import com.marchenko.medcards.service.TestEntityGenerator;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    @Test
    public void login() throws Exception{
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
    }

    @Test
    public void findDoctors() throws Exception {
        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Set<Doctor> foundDoctors = new HashSet<>(testEntityGenerator.getDoctors());
        Doctor findingDoctor =testEntityGenerator.getDoctors().get(0);
        Mockito.when(doctorService.findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization",findingDoctor.getSpecialization())
                .param("doctorSurname",findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService,Mockito.times(1)).findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization","")
                .param("doctorSurname",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService,Mockito.times(1)).findDoctorsBySpecializationAndSurname(Mockito.anyString(), Mockito.anyString());

        Mockito.when(doctorService.findDoctorsBySurname(Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization","")
                .param("doctorSurname",findingDoctor.getSurname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService,Mockito.times(1)).findDoctorsBySurname(Mockito.anyString());

        Mockito.when(doctorService.findDoctorsBySpecialization(Mockito.anyString())).thenReturn(foundDoctors);

        mvc.perform(MockMvcRequestBuilders.get("/patients/{id}/reservations", 1)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patient.getLogin())
                        .password(patient.getPassword())
                        .authorities(patient.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("specialization",findingDoctor.getSpecialization())
                .param("doctorSurname",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctors", foundDoctors))
                .andExpect(MockMvcResultMatchers.view().name("/patients/selectDoctor"));
        Mockito.verify(doctorService,Mockito.times(1)).findDoctorsBySpecialization(Mockito.anyString());

    }

    @Test
    public void selectDoctor() {
    }

    @Test
    public void selectDate() {
    }

    @Test
    public void selectTimeAndPersistReservation() {
    }

    @Test
    public void viewAllReservations() {
    }

    @Test
    public void getSpecializations() {
    }
}

