package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.AppointmentForm;
import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImp implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImp(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment create(Patient patient, LocalDateTime now, Doctor doctor, AppointmentForm form) {
        Appointment appointment = new Appointment(patient, now, doctor, form);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findAppointmentByPatientId(patientId);
    }
}
