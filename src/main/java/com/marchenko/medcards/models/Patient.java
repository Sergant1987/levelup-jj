package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "patients")

@NoArgsConstructor
@Getter
public class Patient implements User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;

    @Setter
    private String login;

    @Setter
    private String password;

    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Setter
    private String phone;
    @Setter
    private String address;

    public Patient(String name, String surname, String login, String password, LocalDate dateOfBirth, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(login, patient.login) && Objects.equals(password, patient.password) && Objects.equals(dateOfBirth, patient.dateOfBirth) && Objects.equals(phone, patient.phone) && Objects.equals(address, patient.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, dateOfBirth, phone, address);
    }

    @Transient
    private final Role role=Role.PATIENT;

}
