package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "patient_id")
    @ManyToOne
    @NotNull
    private Patient patient;

    @JoinColumn(name = "doctor_id")
    @ManyToOne
    @NotNull
    private Doctor doctor;

    @Column(name = "date_reservation")
    @NotNull
    private LocalDate date;

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
