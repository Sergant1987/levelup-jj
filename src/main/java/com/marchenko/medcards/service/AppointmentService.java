package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.AppointmentForm;
import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment create(Patient patient, LocalDateTime now, Doctor doctor, AppointmentForm form);

    List<Appointment> findAppointmentsByPatient(Long patientId);

}
