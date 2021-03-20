package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.repository.DoctorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DoctorServiceTest {
    @Autowired
    DoctorRepository dr;


    @Autowired
    DoctorService doctorService;

    private static List<Doctor> doctors;

    @Before
    public void defore() {
doctors=new TestEntityGenerator().getDoctors();
        dr.deleteAll();
    }


    //  @Before
    public void createDoctors() {
        doctors =new ArrayList<>();
        Doctor doctor1 = new Doctor("loginDoctor1",
                "passwordDoctor1",
                "nameDoctor1",
                "surnameDoctor1",
                LocalDate.of(1965, 3, 21),
                "хирург",
                "+7-999-555-21-56"
        );

        Doctor doctor2 = new Doctor("loginDoctor2",
                "passwordDoctor2",
                "nameDoctor2",
                "surnameDoctor2",
                LocalDate.of(1966, 7, 7),
                "хирург",
                "+7-999-555-65-10"
        );

        Doctor doctor3 = new Doctor("loginDoctor3",
                "passwordDoctor3",
                "nameDoctor3",
                "surnameDoctor3",
                LocalDate.of(1978, 4, 1),
                "окулист",
                "+7-999-555-77-11"
        );

        Doctor doctor4 = new Doctor("loginDoctor4",
                "passwordDoctor4",
                "nameDoctor4",
                "surnameDoctor4",
                LocalDate.of(1981, 2, 1),
                "окулист",
                "+7-999-555-12-31"
        );
        Doctor doctor5 = new Doctor("loginDoctor5",
                "passwordDoctor5",
                "nameDoctor2",
                "surnameDoctor5",
                LocalDate.of(1962, 1, 8),
                "хирург",
                "+7-999-555-65-11"
        );

        Doctor doctor6 = new Doctor("loginDoctor6",
                "passwordDoctor6",
                "nameDoctor6",
                "surnameDoctor4",
                LocalDate.of(1951, 2, 1),
                "терапевт",
                "+7-999-555-12-41"
        );


        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);
        doctors.add(doctor4);
        doctors.add(doctor5);
        doctors.add(doctor6);

    }

    @Test
    public void createWithValidParam() {
        Doctor doctor = doctorService.create(DoctorServiceTest.doctors.get(0).getForm());
        assertEquals(doctors.get(0), doctor);
        Doctor doctor1 = doctorService.findDoctorByLogin(DoctorServiceTest.doctors.get(0).getLogin());
        assertEquals(doctors.get(0), doctor1);
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
        dr.saveAll(doctors);
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorById(doctorExpect.getId());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findDoctorByLogin() {
        dr.saveAll(doctors);
        Doctor doctorExpect = doctors.get(0);
        Doctor doctorActual = null;
        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
        assertEquals(doctorExpect, doctorActual);
    }

    @Test
    public void findAllDoctors() {
        dr.saveAll(doctors);
        List<Doctor> doctorsExpect = doctorService.findAllDoctors();
        assertEquals(doctors.size(), doctorsExpect.size());
    }

    @Test
    public void findAllSpecialization() {
        dr.saveAll(doctors);
        Set<String> specializations = doctorService.findAllSpecialization();
        assertEquals(3, specializations.size());
    }

    @Test
    public void findDoctorsBySpecialization() {
        dr.saveAll(doctors);
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecialization("окулист");
        assertEquals(2, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySpecialization("хирург");
        assertEquals(3, doctorsExpect.size());
    }

    @Test
    public void findDoctorsBySurname() {
        dr.saveAll(doctors);
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
        doctorsExpect = doctorService.findDoctorsBySurname(doctors.get(3).getSurname());
        assertEquals(2, doctorsExpect.size());
    }

    @Test
    public void findDoctorsBySpecializationAndName() {
        dr.saveAll(doctors);
        Set<Doctor> doctorsExpect = doctorService.findDoctorsBySpecializationAndSurname(doctors.get(1).getSpecialization(), doctors.get(1).getSurname());
        assertEquals(1, doctorsExpect.size());
    }

    //    @Test
//    public void createWithValidParam() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        Doctor doctor = doctorService.create(doctors.get(0).getForm());
//        assertEquals(doctors.get(0), doctor);
//        Doctor doctor1 = doctorService.findDoctorByLogin(doctors.get(0).getLogin());
//        assertEquals(doctors.get(0), doctor1);
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void createWithNullParam() {
//        DoctorForm doctorForm = new DoctorForm();
//        Doctor doctor = new Doctor(doctorForm);
//    }
//
//    @Test(expected = ConstraintViolationException.class)
//    public void createWithNotValidParam() {
//        DoctorForm doctorForm = new DoctorForm();
//        doctorForm.setDateOfBirth(LocalDate.now().toString());
//        doctorService.create(doctorForm);
//    }
//
//    @Test
//    public void findDoctorByIdWithValidParam() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        Doctor doctorExpect = doctorService.create(doctors.get(0).getForm());
//        Doctor doctorActual = null;
//        doctorActual = doctorService.findDoctorById(doctorExpect.getId());
//        assertEquals(doctorExpect, doctorActual);
//    }
//
//    @Test
//    public void findDoctorByLogin() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        Doctor doctorExpect = doctorService.create(doctors.get(0).getForm());
//        Doctor doctorActual = null;
//        doctorActual = doctorService.findDoctorByLogin(doctorExpect.getLogin());
//        assertEquals(doctorExpect, doctorActual);
//    }
//
//    @Test
//    public void findAllDoctors() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        dr.saveAll(doctors);
//        List<Doctor> doctorsExpect = doctorService.findAllDoctors();
//        assertEquals(6, doctorsExpect.size());
//    }
//
//    @Test
//    public void findAllSpecialization() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        dr.saveAll(doctors);
//        Set<String> specializations=doctorService.findAllSpecialization();
//        assertEquals(3,specializations.size());
//    }
//
//    @Test
//    public void findDoctorsBySpecialization() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        dr.saveAll(doctors);
//        Set<Doctor> doctorsExpect=doctorService.findDoctorsBySpecialization("окулист");
//        assertEquals(2,doctorsExpect.size());
//        doctorsExpect=doctorService.findDoctorsBySpecialization("хирург");
//        assertEquals(3,doctorsExpect.size());
//    }
//
//    @Test
//    public void findDoctorsBySurname() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        dr.saveAll(doctors);
//        Set<Doctor> doctorsExpect=doctorService.findDoctorsBySurname(doctors.get(1).getSurname());
//        assertEquals(1,doctorsExpect.size());
//        doctorsExpect=doctorService.findDoctorsBySurname(doctors.get(3).getSurname());
//        assertEquals(2,doctorsExpect.size());
//    }
//
//    @Test
//    public void findDoctorsBySpecializationAndName() {
//        List<Doctor> doctors = new TestEntityGenerator().getDoctors();
//        dr.saveAll(doctors);
//        Set<Doctor> doctorsExpect=doctorService.findDoctorsBySpecializationAndSurname(doctors.get(1).getSurname(),doctors.get(1).getSurname());
//        assertEquals(1,doctorsExpect.size());
//    }


}