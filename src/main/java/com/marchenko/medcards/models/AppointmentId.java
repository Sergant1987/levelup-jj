package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Data
public class AppointmentId implements Serializable {

    @Setter
    @PastOrPresent
    private LocalDateTime dateOfAppointment;

    @Setter
    @ManyToOne
//    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public AppointmentId(Patient patient, LocalDateTime dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "AppointmentId{" +
                "dateOfAppointment=" + dateOfAppointment +
                ", patient=" + patient.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentId that = (AppointmentId) o;
        return Objects.equals(dateOfAppointment, that.dateOfAppointment) && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfAppointment, patient);
    }
}

