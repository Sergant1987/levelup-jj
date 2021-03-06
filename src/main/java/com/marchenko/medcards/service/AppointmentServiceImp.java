package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.repository.AppointmentRepository;
import com.marchenko.medcards.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
