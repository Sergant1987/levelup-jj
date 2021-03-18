package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import com.marchenko.medcards.repository.AppointmentRepository;
import com.marchenko.medcards.repository.DoctorRepository;
import com.marchenko.medcards.repository.PatientRepository;
import com.marchenko.medcards.repository.ReservationRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AbstractServiceTest {
    @Autowired
    protected DoctorService doctorService;
    @Autowired
    protected PatientService patientService;
    @Autowired
    protected AppointmentService appointmentService;
    @Autowired
    protected ReservationService reservationService;

    protected TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    @Autowired
    DoctorRepository dr;
    @Autowired
    PatientRepository pr;
    @Autowired
    AppointmentRepository ar;
    @Autowired
    ReservationRepository rr;

    @Before
    public void dropTable() {
        dr.deleteAll();
        pr.deleteAll();
        ar.deleteAll();
        rr.deleteAll();
    }


    protected void saveDoctorsToDB() {
        List<Doctor> doctors = testEntityGenerator.getDoctors();
        for (Doctor d : doctors
        ) {
            doctorService.create(d);
        }
    }

    protected void savePatientsToDB() {
        List<Patient> patients = testEntityGenerator.getPatients();
        for (Patient p : patients
        ) {
            patientService.create(p);
        }
    }

    protected void saveAppointmentsToDB() {
        List<Appointment> appointments = testEntityGenerator.getAppointments();
        for (Appointment a : appointments
        ) {
            System.out.println(a);
            appointmentService.create(
                    a.getAppointmentId().getPatient(),
                    a.getAppointmentId().getDateOfAppointment(),
                    a.getDoctor(),
                    new AppointmentForm(a.getDiagnosis(), a.getDataAppointment()));
        }
    }

    protected void saveReservationsToDB() {
        List<Reservation> reservations = testEntityGenerator.getReservations();
        for (Reservation r : reservations
        ) {
            reservationService.create(r.getPatient(),
                    r.getDoctor(),
                    r.getDate(),
                    r.getTime()
            );
        }
    }


    protected void saveToDB() {
        saveDoctorsToDB();
        savePatientsToDB();
        saveAppointmentsToDB();
        saveReservationsToDB();
    }
}
