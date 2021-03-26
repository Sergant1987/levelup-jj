package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PatientForm extends ContactDataForm {
    @NotBlank
    private String address;

    public PatientForm(String login, String password, String name, String surname, String dateOfBirth, String phone, String address) {
        super(login, password, name, surname, dateOfBirth, phone);
        this.address = address;
    }
}
