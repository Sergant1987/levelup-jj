package com.marchenko.medcards.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class AppointmentId implements Serializable {


    @Setter
    @Column(name = "date_of_appointment", nullable = false)
    private LocalDateTime dateOfAppointment;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public AppointmentId(Patient patient, LocalDateTime dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
        this.patient = patient;
    }
}
