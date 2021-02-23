package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.Doctor;

public interface AppointmentService {
    Appointment create(Appointment appointment);
}
