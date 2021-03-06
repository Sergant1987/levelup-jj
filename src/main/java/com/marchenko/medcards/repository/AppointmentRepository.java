package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Appointment;
import com.marchenko.medcards.models.AppointmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, AppointmentId>, JpaSpecificationExecutor<Appointment> {

    @Query("from Appointment a where a.appointmentId.patient.id=:patientId")
    List<Appointment> findAppointmentByPatientId(Long patientId);

}
