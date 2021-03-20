package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.repository.AppointmentRepository;
import com.marchenko.medcards.repository.DoctorRepository;
import com.marchenko.medcards.repository.PatientRepository;
import com.marchenko.medcards.repository.ReservationRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Ignore
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractServiceTest {

    protected TestEntityGenerator testEntityGenerator=new TestEntityGenerator();;

    @Autowired
    DoctorRepository dr;
    @Autowired
    PatientRepository pr;
    @Autowired
    AppointmentRepository ar;
    @Autowired
    ReservationRepository rr;

    @Before
    public void refreshData() {
        testEntityGenerator.refresh();
    }


    protected void saveDoctorsToDB() {
        List<Doctor> doctors = testEntityGenerator.getDoctors();
        dr.saveAll(doctors);
    }

    protected void savePatientsToDB() {
        List<Patient> patients = testEntityGenerator.getPatients();
      pr.saveAll(patients);
    }

    protected void saveAppointmentsToDB() {
        List<Appointment> appointments = testEntityGenerator.getAppointments();
       ar.saveAll(appointments);
    }

    protected void saveReservationsToDB() {
        List<Reservation> reservations = testEntityGenerator.getReservations();
     rr.saveAll(reservations);
    }

    protected void saveToDB() {
        saveDoctorsToDB();
        savePatientsToDB();
        saveAppointmentsToDB();
        saveReservationsToDB();
    }
}
