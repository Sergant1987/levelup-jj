package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.AppointmentId;
import com.marchenko.medcards.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentRepository extends JpaRepository<Appointment, AppointmentId>, JpaSpecificationExecutor<Appointment> {
}
