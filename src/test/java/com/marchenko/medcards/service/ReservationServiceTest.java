package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;
public class ReservationServiceTest extends AbstractServiceTest {
    @Autowired
    private ReservationService reservationService;

    @Test
    public void testCreateReservationWithValidParam() {
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

    @Test(expected = ConstraintViolationException.class)
    public void testCreateReservationWithPatientIsNull() {
        savePatientsToDB();
        saveDoctorsToDB();
        Reservation reservationExpect = testEntityGenerator.getReservations().get(0);
        Reservation reservationActual = reservationService.create(
                null,
                reservationExpect.getDoctor(),
                reservationExpect.getDate(),
                reservationExpect.getTime());
        assertEquals(reservationExpect, reservationActual);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateReservationWithDoctorIsNull() {
        savePatientsToDB();
        saveDoctorsToDB();
        Reservation reservationExpect = testEntityGenerator.getReservations().get(0);
        Reservation reservationActual = reservationService.create(
                reservationExpect.getPatient(),
                null,
                reservationExpect.getDate(),
                reservationExpect.getTime());
        assertEquals(reservationExpect, reservationActual);
    }

    @Test
    public void testFindById() {
        savePatientsToDB();
        saveDoctorsToDB();
        saveReservationsToDB();
        Reservation reservationExpect = testEntityGenerator.getReservations().get(0);
        Reservation reservationActual = reservationService.findById(reservationExpect.getId());
        assertEquals(reservationExpect, reservationActual);
    }

    @Test
    public void testFindReservationsByDoctorIdAndDate() {
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
    public void testFindReservationsByPatientAndDateAfterNow() {
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