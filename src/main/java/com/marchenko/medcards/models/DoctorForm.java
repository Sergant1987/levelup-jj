package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DoctorForm extends ContactDataForm {

    @NotBlank
    private String specialization;

    public DoctorForm(String login, String password, String name, String surname, String dateOfBirth, String phone, String specialization) {
        super(login, password, name, surname, dateOfBirth, phone);
        this.specialization = specialization;
    }
}

