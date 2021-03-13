package com.marchenko.medcards.models;

import lombok.Data;

@Data
public class ReservationForm {

    private String date;

    private String time;

    public ReservationForm(String date, String time) {
        this.date = date;
        this.time = time;
    }
}
