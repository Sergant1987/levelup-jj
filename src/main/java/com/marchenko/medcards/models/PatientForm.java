package com.marchenko.medcards.models;

import java.time.LocalDate;

public class PatientForm extends ContactDataForm {

    private String address;

    public PatientForm(String login, String password, String name, String surname, LocalDate dateOfBirth, String phone, String address) {
        super(login, password, name, surname, dateOfBirth, phone);
        this.address = address;
    }
}
