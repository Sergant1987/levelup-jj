package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "Appointments")

@NoArgsConstructor
@Getter
public class Appointment {

    @EmbeddedId
    @AttributeOverride(name = "dateOfAppointment", column = @Column(name = "date_of_appointment", nullable = false))
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private AppointmentId appointmentId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    private Doctor doctor;

    @Column(nullable = false, length = 1000)
    @Setter
    @NotBlank
    private String diagnosis;

    @Column(name = "data_appointment", nullable = false)
    @Setter
    @NotBlank
    private String dataAppointment;

    public Appointment(Patient patient, LocalDateTime dateOfAppointment, Doctor doctor, String diagnosis, String dataAppointment) {
        this.appointmentId = new AppointmentId(patient, dateOfAppointment);
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.dataAppointment = dataAppointment;
    }

    public Appointment(Patient patient, LocalDateTime dateOfAppointment, Doctor doctor, AppointmentForm appointmentForm) {
        this.appointmentId = new AppointmentId(patient, dateOfAppointment);
        this.doctor = doctor;
        this.diagnosis = appointmentForm.getDiagnosis();
        this.dataAppointment = appointmentForm.getDataAppointment();
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", doctor=" + doctor.getId() +
                ", diagnosis='" + diagnosis + '\'' +
                ", dataAppointment='" + dataAppointment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return appointmentId.equals(that.appointmentId) && doctor.equals(that.doctor) && diagnosis.equals(that.diagnosis) && dataAppointment.equals(that.dataAppointment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, doctor, diagnosis, dataAppointment);
    }


}
