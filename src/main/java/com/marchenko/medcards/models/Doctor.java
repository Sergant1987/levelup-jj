package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

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
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }

//    public Doctor(String login, String password, Date dateOfBirth, String name, String surname, String specialization, String phone) {
//        this.login = login;
//        this.password = password;
//        this.dateOfBirth = dateOfBirth;
//        this.name = name;
//        this.surname = surname;
//        this.specialization = specialization;
//        this.phone = phone;
//    }
}
