package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.DoctorForm;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.PatientForm;
import com.marchenko.medcards.service.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

import static org.junit.Assert.*;

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
    @MockBean
    private PasswordEncoder passwordEncoder;

    private TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    @Test
    public void index() throws Exception {
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
    public void loginPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/doctors/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/login"));
    }

    @Test
    public void registration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/doctors/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/doctors/registration"));
    }

    @Test
    public void postRegistration() throws Exception {
        Doctor doctorExpect = testEntityGenerator.getDoctors().get(0);
        doctorExpect.setId(1L);

        Mockito.when(doctorService.create(Mockito.mock(Doctor.class))).thenReturn(doctorExpect);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/registration")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("doctorForm", doctorExpect.getForm()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/doctors/%d", doctorExpect.getId())));
        Mockito.verify(doctorService, Mockito.times(1)).create(doctorExpect.getForm());
    }

    @Test
    public void viewDoctorMenu() throws Exception {
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
    public void viewSchedule() {
    }

    @Test
    public void selectReservation() {
    }

    @Test
    public void findPatient() throws Exception {
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
    public void selectPatient() throws Exception {
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        doctor.setId(1L);
        Patient foundPatient = testEntityGenerator.getPatients().get(0);
        foundPatient.setId(1L);

        mvc.perform(MockMvcRequestBuilders.post("/doctors/{id}/appointments", doctor.getId())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(doctor.getLogin())
                        .password(doctor.getPassword())
                        .authorities(doctor.getRole().getAuthorities()))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                //TODO КАК передать объект
                .param("patientId", foundPatient.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        String.format("/doctors/%d/appointments/%d", doctor.getId(), foundPatient.getId())));
    }


    @Test
    public void viewFormCreateAppointment() {
    }

    @Test
    public void createAppointment() {
    }
}