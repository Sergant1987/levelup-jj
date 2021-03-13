package com.marchenko.medcards.models;

import lombok.Data;

@Data
public class AppointmentForm {

    private String diagnosis;

    private String dataAppointment;

    public AppointmentForm(String diagnosis, String dataAppointment) {
        this.diagnosis = diagnosis;
        this.dataAppointment = dataAppointment;
    }
}
