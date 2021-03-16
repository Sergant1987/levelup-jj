package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
}
