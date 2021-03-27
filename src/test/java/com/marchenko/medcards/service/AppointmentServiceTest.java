package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static org.junit.Assert.*;

public class AppointmentServiceTest extends AbstractServiceTest {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testCreateWithValidParam() {
        savePatientsToDB();
        saveDoctorsToDB();
        Appointment appointmentExpect = testEntityGenerator.getAppointments().get(0);

                Appointment appointmentActual = appointmentService.create(appointmentExpect.getAppointmentId().getPatient(),
                appointmentExpect.getAppointmentId().getDateOfAppointment(),
                appointmentExpect.getDoctor(),
                new AppointmentForm(appointmentExpect.getDiagnosis(),
                        appointmentExpect.getDataAppointment()));
        assertEquals(appointmentExpect, appointmentActual);
    }

    @Test
    public void testFindAppointmentsByPatient() {
        saveToDB();
        List<Appointment> appointments = testEntityGenerator.getAppointments();

        List<Appointment> appointmentActual = appointmentService.findAppointmentsByPatient(
                appointments.get(0).getAppointmentId().getPatient().getId());

        assertEquals(2, appointmentActual.size());
    }

}