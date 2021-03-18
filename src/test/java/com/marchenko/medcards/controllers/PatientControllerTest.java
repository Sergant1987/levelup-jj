package com.marchenko.medcards.controllers;

import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.TestEntityGenerator;
import com.marchenko.medcards.service.AppointmentService;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.ReservationService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.Assert.*;

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

/*
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView postRegistration(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String dateOfBirth,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {
        Patient patient =
                new Patient(login, passwordEncoder.encode(password),
                        name, surname, LocalDate.parse(dateOfBirth),
                        phone, address);

        patientService.create(patient);
        return new RedirectView("/patients/" + patient.getId());
    }
*/