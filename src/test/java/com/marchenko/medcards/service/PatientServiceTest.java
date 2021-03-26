package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.PatientForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class PatientServiceTest extends AbstractServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void createWithValidParam() {
        List<Patient> patients = testEntityGenerator.getPatients();
        Patient patient = patientService.create(patients.get(0).getForm());
        assertEquals(patients.get(0), patient);
        Patient patient1 = patientService.findPatientByLogin(patients.get(0).getLogin());
        assertEquals(patients.get(0), patient1);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullParam() {
        PatientForm patientForm = new PatientForm();
        new Patient(patientForm);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNotValidParam() {
        PatientForm patientForm = new PatientForm();
        patientForm.setDateOfBirth(LocalDate.now().toString());
        patientService.create(patientForm);
    }

    @Test
    public void findPatientByIdWithValidParam() {
        savePatientsToDB();
        Patient patientExpect = testEntityGenerator.getPatients().get(0);
        Patient patientActual = patientService.findPatientById(patientExpect.getId());
        assertEquals(patientExpect, patientActual);
    }

    @Test
    public void findPatientByLoginWithValidParam() {
        savePatientsToDB();
        Patient patientExpect = testEntityGenerator.getPatients().get(0);
        Patient patientActual = patientService.findPatientByLogin(patientExpect.getLogin());
        assertEquals(patientExpect, patientActual);
    }

    @Test
    public void findByNameOrSurnameOrPhone() {
        savePatientsToDB();
        List<Patient> patients = testEntityGenerator.getPatients();

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

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone(patients.get(0).getName().substring(0, 5), "", "");
        System.out.println(actualPatients.size());
        assertEquals(6, actualPatients.size());

        actualPatients = patientService.findPatientsByNameAndSurnameAndPhone("", patients.get(0).getName().substring(0, 4), "");
        System.out.println(actualPatients.size());
        assertEquals(6, actualPatients.size());

    }
}