package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "patients")

@Getter
public class Patient extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;


    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String surname;

    @Setter
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Setter
    @Column(nullable = false, unique = true)
    private String phone;

    @Setter
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "patient")
    private final Collection<Reservation> reservations = new ArrayList<>();

    public Patient(String login, String password,String name, String surname,  LocalDate dateOfBirth, String phone, String address) {
        super(login, password, Role.PATIENT);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;

    }

    public Patient(PatientForm form, String address){
        super(form.getLogin(), form.getPassword(), Role.DOCTOR);
        this.dateOfBirth = form.getDateOfBirth();
        this.name = form.getName();
        this.surname = form.getSurname();
        this.address = address;
        this.phone = form.getPhone();
    }

    public Patient() {
        role=Role.PATIENT;
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
        return Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname)
                && Objects.equals(login, patient.login) && Objects.equals(password, patient.password)
                && Objects.equals(dateOfBirth, patient.dateOfBirth) && Objects.equals(phone, patient.phone)
                && Objects.equals(address, patient.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, dateOfBirth, phone, address);
    }


}
