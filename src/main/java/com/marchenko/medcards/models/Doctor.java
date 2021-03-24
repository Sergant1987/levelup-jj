package com.marchenko.medcards.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "doctors")

@Getter
public class Doctor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @PositiveOrZero
    private Long id;

    @Setter
    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Setter
    @Column(nullable = false, length = 50)
    @NotBlank
    @Length(max = 50)
    private String name;

    @Setter
    @Column(nullable = false, length = 50)
    @NotBlank
    @Length(max = 50)
    private String surname;

    @Setter
    @Column(nullable = false, length = 50)
    @NotBlank
    @Length(max = 50)
    private String specialization;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    @Length(min = 10,max = 20)
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phone;

    @OneToMany(mappedBy = "doctor")
    private final Collection<Reservation> reservations = new ArrayList<>();

    public Doctor(String login, String password, String name, String surname, LocalDate dateOfBirth, String specialization, String phone) {
        super(login, password, Role.DOCTOR);
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.phone = phone;
    }

    public Doctor(DoctorForm form) {
        super(form.getLogin(), form.getPassword(), Role.DOCTOR);
        this.dateOfBirth = LocalDate.parse(form.getDateOfBirth());
        this.name = form.getName();
        this.surname = form.getSurname();
        this.specialization = form.getSpecialization();
        this.phone = form.getPhone();
    }

    public DoctorForm getForm() {
        return new DoctorForm(login, password, name, surname, dateOfBirth.toString(), phone, specialization);
    }

    public Doctor() {
        role = Role.DOCTOR;
    }

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
