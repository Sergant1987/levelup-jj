package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class DoctorServiceTest extends AbstractServiceTest {

    @Autowired
   private DoctorService doctorService;

    @Test
    public void TestCreateWithValidParam() {
        Doctor doctorExpect=testEntityGenerator.getDoctors().get(0);
        Doctor doctorActual = doctorService.create(doctorExpect.getForm());
        assertEquals(doctorExpect, doctorActual);
        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullParam() {
        DoctorForm doctorForm = new DoctorForm();
        new Doctor(doctorForm);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithDateOfBirthNotValid() {
        DoctorForm doctorForm = new DoctorForm();
        doctorForm.setDateOfBirth(LocalDate.now().toString());
        doctorService.create(doctorForm);
    }

    @Test
    public void testFindDoctorByIdWithValidParam() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = doctorService.findDoctorById(doctorExpect.getId());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void testFindDoctorByLogin() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void testFindAllDoctors() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        List<Doctor> doctorsExpect = doctorService.findAllDoctors();
        assertEquals(doctors.size(), doctorsExpect.size());
    }

    @Test
    public void testFindAllSpecialization() {
        saveDoctorsToDB();
        Set<String> specializations = doctorService.findAllSpecialization();
        assertEquals(3, specializations.size());
    }

    @Test
    public void testFindDoctorsBySpecialization() {
        saveDoctorsToDB();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecialization("окулист");
        assertEquals(2, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySpecialization("хирург");
        assertEquals(3, doctorsExpect.size());
    }

    @Test
    public void testFindDoctorsBySurname() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(3).getSurname());
        assertEquals(2, doctorsExpect.size());
    }

    @Test
    public void testFindDoctorsBySpecializationAndName() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecializationAndSurname(doctors.get(1).getSpecialization(), doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
    }
}