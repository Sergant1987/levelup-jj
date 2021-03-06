package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.Patient;

import java.util.List;

public interface AppointmentService {
    Appointment create(Appointment appointment);

    List<Appointment> findAllByPatient(Patient patient);

    List<Appointment> findAllByPatient(Long patientId);


}
