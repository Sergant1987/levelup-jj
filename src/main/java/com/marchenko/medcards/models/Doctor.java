package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "doctors")

@NoArgsConstructor
@Getter

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String login;

    @Setter
    private String password;

    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Setter
    private String name;

    @Setter
    private String surname;

    @Setter
    private String specialization;

    @Setter
    private String phone;

    @Override
    public String toString() {
        return "Doctor{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specialization='" + specialization + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Doctor(String login, String password, LocalDate dateOfBirth, String name, String surname, String specialization, String phone) {
        this.login = login;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(login, doctor.login) && Objects.equals(password, doctor.password)
                && Objects.equals(dateOfBirth, doctor.dateOfBirth) && Objects.equals(name, doctor.name)
                && Objects.equals(surname, doctor.surname) && Objects.equals(specialization, doctor.specialization)
                && Objects.equals(phone, doctor.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, dateOfBirth, name, surname, specialization, phone);
    }
}
