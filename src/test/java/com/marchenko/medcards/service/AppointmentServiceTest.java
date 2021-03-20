package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import java.util.List;
//@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AppointmentServiceTest  extends AbstractServiceTest{
    @Autowired
    protected DoctorService doctorService;
    @Autowired
    protected PatientService patientService;
    @Autowired
    protected AppointmentService appointmentService;

    @Test
    public void createWithValidParam() {
        savePatientsToDB();
        Patient patient = testEntityGenerator.getPatients().get(0);
        saveDoctorsToDB();
        Doctor doctor = testEntityGenerator.getDoctors().get(0);
        Appointment appointmentExpect = new Appointment(patient,
                LocalDateTime.now(),
                doctor,
                "diagnosis1",
                "dataAppointment1");

        Appointment appointmentActual = appointmentService.create(appointmentExpect.getAppointmentId().getPatient(),
                appointmentExpect.getAppointmentId().getDateOfAppointment(),
                appointmentExpect.getDoctor(),
                new AppointmentForm(appointmentExpect.getDiagnosis(),
                        appointmentExpect.getDataAppointment()));
        assertEquals(appointmentExpect, appointmentActual);
    }

    @Test
    public void findAppointmentsByPatient() {
        saveToDB();
        List<Appointment> appointments = testEntityGenerator.getAppointments();

        List<Appointment> appointmentActual = appointmentService.findAppointmentsByPatient(
                appointments.get(0).getAppointmentId().getPatient().getId());

        assertEquals(2, appointmentActual.size());
    }

}