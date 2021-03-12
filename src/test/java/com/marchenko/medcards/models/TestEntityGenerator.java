package com.marchenko.medcards.models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TestEntityGenerator {
    private final static List<Doctor> doctors = new ArrayList<>();
    private final static List<Patient> patients = new ArrayList<>();
    private final static List<Reservation> reservations = new ArrayList<>();
    private final static List<Appointment> appointments = new ArrayList<>();


    static {
        createDoctors();
        createPatients();
        createReservations();
        createAppointments();
    }

    private static void createDoctors() {
        Doctor doctor1 = new Doctor("loginDoctor1"
                , "passwordDoctor1"
                , LocalDate.of(1965, 3, 21)
                , "nameDoctor1",
                "surnameDoctor1",
                "хирург",
                "+7-999-555-21-56"
        );

        Doctor doctor2 = new Doctor("loginDoctor2"
                , "passwordDoctor2"
                , LocalDate.of(1966, 7, 7)
                , "nameDoctor2",
                "surnameDoctor2",
                "хирург",
                "+7-999-555-65-10"
        );

        Doctor doctor3 = new Doctor("loginDoctor3"
                , "passwordDoctor3"
                , LocalDate.of(1978, 4, 1)
                , "nameDoctor3",
                "surnameDoctor3",
                "окулист",
                "+7-999-555-77-11"
        );

        Doctor doctor4 = new Doctor("loginDoctor4"
                , "passwordDoctor4"
                , LocalDate.of(1981, 2, 1)
                , "nameDoctor4",
                "surnameDoctor4",
                "окулист",
                "+7-999-555-12-31"
        );
        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);
        doctors.add(doctor4);

    }

    private static void createPatients() {
        Patient patient1 = new Patient("loginPatient1"
                , "passwordPatient1"
                , "namePatient1",
                "surnamePatient1"
                , LocalDate.of(1988, 2, 1),
                "+7-9555-999-12-31",
                "addressPatient1"
        );

        Patient patient2 = new Patient("loginPatient2"
                , "passwordPatient2"
                , "namePatient2",
                "surnamePatient2"
                , LocalDate.of(1980, 7, 3),
                "+7-9555-999-32-35",
                "addressPatient2"
        );

        Patient patient3 = new Patient("loginPatient3"
                , "passwordPatient3"
                , "namePatient3",
                "surnamePatient3"
                , LocalDate.of(1955, 5, 6),
                "+7-9555-999-22-76",
                "addressPatient3"
        );

        Patient patient4 = new Patient("loginPatient4"
                , "passwordPatient4"
                , "namePatient4",
                "surnamePatient4"
                , LocalDate.of(1978, 5, 22),
                "+7-9555-999-66-77",
                "addressPatient4"
        );
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);
        patients.add(patient4);
    }

    private static void createReservations() {
        Reservation reservation1 = new Reservation(
                patients.get(0),
                doctors.get(0),
                LocalDate.of(2021, 3, 12),
                TimeReservation.ELEVEN_ZERO);

        Reservation reservation2 = new Reservation(
                patients.get(0),
                doctors.get(0),
                LocalDate.of(2021, 4, 15),
                TimeReservation.THIRTEEN_ZERO);

        Reservation reservation3 = new Reservation(
                patients.get(1),
                doctors.get(1),
                LocalDate.of(2021, 6, 10),
                TimeReservation.FIFTEEN_HALF);

        Reservation reservation4 = new Reservation(
                patients.get(2),
                doctors.get(2),
                LocalDate.of(2021, 1, 1),
                TimeReservation.ELEVEN_HALF);
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        reservations.add(reservation4);
    }

    private static void createAppointments() {
        Appointment appointment1 = new Appointment(
                patients.get(0),
                LocalDateTime.of(2021, 3, 12, 11, 22),
                doctors.get(0),
                "diagnosis1",
                "dataAppointment1"
        );

      Appointment appointment2 = new Appointment(
                patients.get(0),
                LocalDateTime.of(2021, 4, 15, 13, 15),
                doctors.get(0),
                "diagnosis2",
                "dataAppointment2"
        );

      Appointment appointment3 = new Appointment(
                patients.get(0),
                LocalDateTime.of(2021, 6, 10, 15, 56),
                doctors.get(0),
                "diagnosis3",
                "dataAppointment3"
        );

    Appointment appointment4 = new Appointment(
                patients.get(0),
                LocalDateTime.of(2021, 1, 1, 11, 38),
                doctors.get(0),
                "diagnosis4",
                "dataAppointment4"
        );
appointments.add(appointment1);
appointments.add(appointment2);
appointments.add(appointment3);
appointments.add(appointment4);
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static List<Patient> getPatients() {
        return patients;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static List<Appointment> getAppointments() {
        return appointments;
    }
}
