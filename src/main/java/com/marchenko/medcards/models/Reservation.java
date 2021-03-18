package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.Objects;


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

    public Reservation(Patient patient, Doctor doctor, LocalDate date, TimeReservation time) {
        this.patient = patient;
        this.doctor = doctor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor) && Objects.equals(date, that.date) && time == that.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, doctor, date, time);
    }
}
