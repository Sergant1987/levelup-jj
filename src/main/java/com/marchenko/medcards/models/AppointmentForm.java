package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@NoArgsConstructor
public class AppointmentForm {
    @NotBlank
    private String diagnosis;
    @NotBlank
    private String dataAppointment;

    public AppointmentForm(String diagnosis, String dataAppointment) {
        this.diagnosis = diagnosis;
        this.dataAppointment = dataAppointment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentForm that = (AppointmentForm) o;
        return Objects.equals(diagnosis, that.diagnosis) && Objects.equals(dataAppointment, that.dataAppointment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagnosis, dataAppointment);
    }

}
