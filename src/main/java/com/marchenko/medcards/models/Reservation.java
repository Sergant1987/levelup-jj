package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "patient_id")
    @ManyToOne
    private Patient patient;

    @Setter
    @JoinColumn(name = "doctor_id")
    @ManyToOne
    private Doctor doctor;

    @Setter
    @Column(name = "date_reservation")
    private LocalDate dateRecord;

    public Reservation(Patient patient, Doctor doctorId, LocalDate dateRecord) {
        this.patient = patient;
        this.doctor = doctorId;
        this.dateRecord = dateRecord;
    }
}
