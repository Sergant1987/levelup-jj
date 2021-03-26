package com.marchenko.medcards.security;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import com.marchenko.medcards.service.TestEntityGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsServiceTest {
    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();
    private final Patient patient = testEntityGenerator.getPatients().get(0);
    private final Doctor doctor = testEntityGenerator.getDoctors().get(0);

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void before() {
        Mockito.when(doctorService.findDoctorByLogin(doctor.getLogin())).thenReturn(doctor);
        Mockito.when(patientService.findPatientByLogin(patient.getLogin())).thenReturn(patient);
    }

    @Test
    public void testUserDoctor() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(doctor.getLogin());
        assertEquals(doctor.getLogin(), userDetails.getUsername());
        assertEquals(doctor.getPassword(), userDetails.getPassword());
        assertEquals(doctor.getRole().getAuthorities(), userDetails.getAuthorities());
    }

    @Test
    public void testUserPatient() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(patient.getLogin());
        assertEquals(patient.getLogin(), userDetails.getUsername());
        assertEquals(patient.getPassword(), userDetails.getPassword());
        assertEquals(patient.getRole().getAuthorities(), userDetails.getAuthorities());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testNonExistingUser() {
        userDetailsService.loadUserByUsername("user-123");
    }


}