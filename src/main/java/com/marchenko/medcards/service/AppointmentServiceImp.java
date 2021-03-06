package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.repository.AppointmentRepository;
import com.marchenko.medcards.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByPatient(Patient patient) {
        return appointmentRepository.findAllByPatient(patient);
    }

    @Override
    public List<Appointment> findAllByPatient(Long patientId) {
        return appointmentRepository.findAllByPatient(patientId);
    }
}
