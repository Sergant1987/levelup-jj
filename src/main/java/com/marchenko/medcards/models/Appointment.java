package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Appointments")

@NoArgsConstructor
@Getter
public class Appointment {

    @EmbeddedId
    private AppointmentId appointmentId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;

    @Column(nullable = false, length = 1000)
    @Setter
    private String diagnosis;

    @Column(name = "data_appointment", nullable = false, length = 10000)
    @Setter
    private String dateAppointment;

    public Appointment(Patient patient, LocalDateTime dateOfAppointment, Doctor doctor, String diagnosis, String dateAppointment) {
        this.appointmentId = new AppointmentId(patient,dateOfAppointment);
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.dateAppointment = dateAppointment;
    }
}
