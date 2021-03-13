package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ContactDataForm {

    private String login;

    private String password;

    private String name;

    private String surname;

    private String dateOfBirth;

    private String phone;

    public ContactDataForm(String login, String password, String name, String surname, String dateOfBirth, String phone) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }
}
