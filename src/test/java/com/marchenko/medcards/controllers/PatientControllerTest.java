package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Role;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

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

        mvc.perform(MockMvcRequestBuilders.get("/patient/")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(patientExpect.getLogin())
                                .password(patientExpect.getPassword())
                                .authorities(patientExpect.getRole().getAuthorities())
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d", patientExpect.getId())));
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
                .param("name", patientExpect.getName())
                .param("surname", patientExpect.getSurname())
                .param("dateOfBirth", patientExpect.getDateOfBirth().toString())
                .param("login", patientExpect.getLogin())
                .param("password", patientExpect.getPassword())
                .param("phone", patientExpect.getPhone())
                .param("address", patientExpect.getAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/patients/%d", patientExpect.getId())));
    }


}

