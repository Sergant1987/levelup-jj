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
    public void createWithValidParam() {
        Doctor doctorExpect=testEntityGenerator.getDoctors().get(0);
        Doctor doctorActual = doctorService.create(doctorExpect.getForm());
        assertEquals(doctorExpect, doctorActual);
        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullParam() {
        DoctorForm doctorForm = new DoctorForm();
        Doctor doctor = new Doctor(doctorForm);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNotValidParam() {
        DoctorForm doctorForm = new DoctorForm();
        doctorForm.setDateOfBirth(LocalDate.now().toString());
        doctorService.create(doctorForm);
    }

    @Test
    public void findDoctorByIdWithValidParam() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorById(doctorExpect.getId());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findDoctorByLogin() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findAllDoctors() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        List<Doctor> doctorsExpect = doctorService.findAllDoctors();
        assertEquals(doctors.size(), doctorsExpect.size());
    }

    @Test
    public void findAllSpecialization() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<String> specializations = doctorService.findAllSpecialization();
        assertEquals(3, specializations.size());
    }

    @Test
    public void findDoctorsBySpecialization() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecialization("окулист");
        assertEquals(2, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySpecialization("хирург");
        assertEquals(3, doctorsExpect.size());
    }

    @Test
    public void findDoctorsBySurname() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(3).getSurname());
        assertEquals(2, doctorsExpect.size());
    }

    @Test
    public void findDoctorsBySpecializationAndName() {
        saveDoctorsToDB();
        List<Doctor> doctors=testEntityGenerator.getDoctors();
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecializationAndSurname(doctors.get(1).getSpecialization(), doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
    }
}