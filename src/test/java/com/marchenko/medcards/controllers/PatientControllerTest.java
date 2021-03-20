package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Patient;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest{

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

    private TestEntityGenerator testEntityGenerator=new TestEntityGenerator();

    @Test

    public void index() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get(""))

    }

    @Test
    public void registration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/patients/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/patients/registration"));
    }

    @Test
    public void postRegistration() throws Exception {

        Patient patient = testEntityGenerator.getPatients().get(0);
        patient.setId(1L);

        Mockito.when(patientService.create(patient.getForm())).thenReturn(patient);
        mvc.perform(MockMvcRequestBuilders.post("/patients/registration")
              .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous())
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", patient.getName())
                .param("surname", patient.getSurname())
                .param("dateOfBirth", patient.getDateOfBirth().toString())
                .param("login", patient.getLogin())
                .param("password", patient.getPassword())
                .param("phone", patient.getPhone())
                .param("address", patient.getAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patients/" + patient.getId()));
    }


}

