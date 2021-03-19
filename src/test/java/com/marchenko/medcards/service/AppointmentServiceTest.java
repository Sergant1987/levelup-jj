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
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AppointmentServiceTest extends AbstractServiceTest {

    @Test
    public void createWithValidParam() {

        Patient patient = patientService.create(new Patient("loginPatient1"
                , "passwordPatient1"
                , "namePatient1",
                "surnamePatient1"
                , LocalDate.of(1988, 2, 1),
                "+7-955-999-12-31",
                "addressPatient1"
        ));

        Doctor doctor = doctorService.create(new Doctor("loginDoctor1",
                "passwordDoctor1",
                "nameDoctor1",
                "surnameDoctor1",
                LocalDate.of(1965, 3, 21),
                "хирург",
                "+7-999-555-21-56"
        ));

        Appointment appointmentExpect = new Appointment(patient,
                LocalDateTime.now(),
                doctor,
                "diagnosis1",
                "dataAppointment1");
        System.out.println("appointmentExpect");
        System.out.println(appointmentExpect);
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