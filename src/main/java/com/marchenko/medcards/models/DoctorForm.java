package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
public class DoctorForm extends ContactDataForm{


    private String specialization;

    public DoctorForm(String login, String password, String name, String surname, String dateOfBirth, String phone, String specialization) {
        super(login, password, name, surname, dateOfBirth, phone);
        this.specialization = specialization;
    }
}

