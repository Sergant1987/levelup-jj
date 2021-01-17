package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "patients")

@NoArgsConstructor
@Getter
public class Patient {
    @Id
    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;

    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String phone;

    private String address;

    public Patient(String name, String surname, LocalDate dateOfBirth, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(dateOfBirth, patient.dateOfBirth) && Objects.equals(phone, patient.phone) && Objects.equals(address, patient.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, dateOfBirth, phone, address);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
