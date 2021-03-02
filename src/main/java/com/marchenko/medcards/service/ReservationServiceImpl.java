package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.models.TimeReservation;
import com.marchenko.medcards.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(Patient patient, Doctor doctor, LocalDate date, TimeReservation time) {
        Reservation reservation = new Reservation(patient, doctor, date, time);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findRecordsByDoctorAndDate(Doctor doctor, LocalDate date) {
        return null;
    }

    @Override
    public List<Reservation> findRecordsByPatient(Patient patient) {
        return null;
    }
}