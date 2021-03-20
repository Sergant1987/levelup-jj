package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.Assert.*;
public class ReservationServiceTest extends AbstractServiceTest{
    @Autowired
    private ReservationService reservationService;

    @Test
    public void create() {
        savePatientsToDB();
        saveDoctorsToDB();
        Reservation reservationExpect = testEntityGenerator.getReservations().get(0);
        Reservation reservationActual = reservationService.create(
                reservationExpect.getPatient(),
                reservationExpect.getDoctor(),
                reservationExpect.getDate(),
                reservationExpect.getTime());
        assertEquals(reservationExpect, reservationActual);
    }

    @Test
    public void findById() {
        savePatientsToDB();
        saveDoctorsToDB();
        saveReservationsToDB();
        Reservation reservationExpect = testEntityGenerator.getReservations().get(0);
        Reservation reservationActual = reservationService.findById(reservationExpect.getId());
        assertEquals(reservationExpect, reservationActual);
    }

    @Test
    public void findReservationsByDoctorIdAndDate() {
        savePatientsToDB();
        saveDoctorsToDB();
        saveReservationsToDB();

        Reservation reservation = testEntityGenerator.getReservations().get(0);
        List<Reservation> reservationActual = reservationService.findReservationsByDoctorIdAndDate(reservation.getDoctor().getId(),reservation.getDate());
        assertEquals(2, reservationActual.size());

        reservation = testEntityGenerator.getReservations().get(1);
        reservationActual = reservationService.findReservationsByDoctorIdAndDate(reservation.getDoctor().getId(),reservation.getDate());
        assertEquals(1, reservationActual.size());
    }

    @Test
    public void findReservationsByPatientAndDateAfterNow() {
        savePatientsToDB();
        saveDoctorsToDB();
        saveReservationsToDB();

        Reservation reservation = testEntityGenerator.getReservations().get(0);
        Patient patient=reservation.getPatient();
        List<Reservation> reservationActual = reservationService.findReservationsByPatientIdWhenDateAfterNow(patient.getId());
        assertEquals(2, reservationActual.size());

        reservation = testEntityGenerator.getReservations().get(3);
        patient=reservation.getPatient();
        reservationActual =reservationService.findReservationsByPatientIdWhenDateAfterNow(patient.getId());
        assertEquals(3, reservationActual.size());
    }


}