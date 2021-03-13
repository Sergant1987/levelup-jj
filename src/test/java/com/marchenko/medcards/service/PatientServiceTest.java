package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.PatientForm;
import com.marchenko.medcards.models.TestEntityGenerator;
import com.marchenko.medcards.repository.PatientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PatientServiceTest {

    @Autowired
    PatientService patientService;

//    @Before
//    public void deleteData() {
//        patientRepository.deleteAll();
//    }

    @Test
    public void createWithValidParam() {
        List<Patient> patients = TestEntityGenerator.getPatients();
        Patient patient = patientService.create(patients.get(0).getForm());
        assertEquals(patients.get(0), patient);
        Patient patient1 = patientService.findPatientByLogin(patients.get(0).getLogin());
        assertEquals(patients.get(0), patient1);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullParam() {
        PatientForm patientForm = new PatientForm();
        Patient patient = new Patient(patientForm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNotValidParam() {
        PatientForm patientForm = new PatientForm();
        patientForm.setDateOfBirth(LocalDate.now().toString());
        patientService.create(patientForm);
    }

    @Test
    public void findPatientByIdWithValidParam() {
        List<Patient> patients = TestEntityGenerator.getPatients();
        Patient patientExpect = patientService.create(patients.get(0).getForm());
        Patient patientActual = null;
        patientActual = patientService.findPatientById(patientExpect.getId());
        assertEquals(patientExpect, patientActual);
    }

    @Test
    public void findPatientByLoginWithValidParam() {
        List<Patient> patients = TestEntityGenerator.getPatients();
        Patient patientExpect = patientService.create(patients.get(0).getForm());
        Patient patientActual = null;
        patientActual = patientService.findPatientByLogin(patientExpect.getLogin());
        assertEquals(patientExpect, patientActual);
    }

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void findByNameOrSurnameOrPhone() {
        List<Patient> patients = TestEntityGenerator.getPatients();
        for (Patient p : patients
        ) {
            patientService.create(p.getForm());
        }

        List<Patient> actualPatients = patientService.findPatientsByNameAndSurnameAndPhone(patients.get(0).getName(), patients.get(0).getSurname(), patients.get(0).getPhone());
        assertEquals(1, actualPatients.size());
        assertEquals(patients.get(0), actualPatients.get(0));

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone("", "", "");
        assertEquals(0, actualPatients.size());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone(null, null, null);
        assertEquals(0, actualPatients.size());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone("", "", patients.get(0).getPhone());
        assertEquals(1, actualPatients.size());
        assertEquals(patients.get(0).getPhone(), actualPatients.get(0).getPhone());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone(patients.get(0).getName(), "", "");
        System.out.println(actualPatients.size());
        assertEquals(2, actualPatients.size());
        assertEquals(patients.get(0).getName(), actualPatients.get(0).getName());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone("", patients.get(1).getSurname(), "");
        System.out.println(actualPatients.size());
        assertEquals(2, actualPatients.size());
        assertEquals(patients.get(1).getSurname(), actualPatients.get(0).getSurname());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone(patients.get(0).getName().substring(0,5), "", "");
        System.out.println(actualPatients.size());
        assertEquals(6, actualPatients.size());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone("", patients.get(0).getName().substring(0,4), "");
        System.out.println(actualPatients.size());
        assertEquals(6, actualPatients.size());


    }
}