package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


public class DoctorServiceTest extends AbstractServiceTest{

    @Test
    public void createWithValidParam() {
        List<Doctor> doctors = testEntityGenerator.getDoctors();
        Doctor doctor = doctorService.create(doctors.get(0).getForm());
        assertEquals(doctors.get(0), doctor);
        Doctor doctor1 = doctorService.findDoctorByLogin(doctors.get(0).getLogin());
        assertEquals(doctors.get(0), doctor1);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullParam() {
        DoctorForm doctorForm = new DoctorForm();
        Doctor doctor = new Doctor(doctorForm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNotValidParam() {
        DoctorForm doctorForm = new DoctorForm();
        doctorForm.setDateOfBirth(LocalDate.now().toString());
        doctorService.create(doctorForm);
    }

    @Test
    public void findDoctorByIdWithValidParam() {
        List<Doctor> doctors = testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctorService.create(doctors.get(0).getForm());
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorById(doctorExpect.getId());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findDoctorByLogin() {
        List<Doctor> doctors = testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctorService.create(doctors.get(0).getForm());
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findAllDoctors() {
        saveDoctorsToDB();
        List<Doctor> doctors = doctorService.findAllDoctors();
        assertEquals(6, doctors.size());
    }

    @Test
    public void findAllSpecialization() {
        saveDoctorsToDB();
        Set<String> specializations=doctorService.findAllSpecialization();
        assertEquals(3,specializations.size());
    }

    @Test
    public void findDoctorsBySpecialization() {
        saveDoctorsToDB();
        Set<Doctor> doctors=doctorService.findDoctorsBySpecialization("окулист");
        assertEquals(2,doctors.size());
        doctors=doctorService.findDoctorsBySpecialization("хирург");
        assertEquals(3,doctors.size());
    }

    @Test
    public void findDoctorsBySurname() {

    }

    @Test
    public void findDoctorsBySpecializationAndName() {
    }


}