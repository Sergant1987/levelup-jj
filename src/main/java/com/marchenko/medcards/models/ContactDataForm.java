package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 50)
    private String name;
    @NotBlank
    @Length(max = 50)
    private String surname;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    @Length(min = 10, max = 20)
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
