package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ContactDataForm {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    @Past
    private String dateOfBirth;
    @NotBlank
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
