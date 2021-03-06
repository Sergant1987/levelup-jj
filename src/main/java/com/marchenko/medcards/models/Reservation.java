package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


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
    private LocalDate date;

    @Setter
    @Column(name = "time_reservation")
    @Enumerated(EnumType.STRING)
    private TimeReservation time;

    public Reservation(Patient patient, Doctor doctorId, LocalDate date, TimeReservation time) {
        this.patient = patient;
        this.doctor = doctorId;
        this.date= date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "patient=" + patient.getId() +
                ", doctor=" + doctor.getId() +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
