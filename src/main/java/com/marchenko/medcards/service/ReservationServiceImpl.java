package com.marchenko.medcards.service;

import com.marchenko.medcards.config.exceptions.NotFoundException;
import com.marchenko.medcards.models.*;
import com.marchenko.medcards.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
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
    public List<Reservation> findReservationsByDoctorIdAndDate(Long doctorId, LocalDate date) {
        return reservationRepository.findReservationsByDoctorIdAndDate(doctorId, date);
    }

    @Override
    public List<Reservation> findReservationsByPatientIdWhenDateAfterNow(Long patientId) {
        return reservationRepository.findByPatientIdWhenDateAfterNow(patientId);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("Reservation with id=%d doesn't exists",id)));
    }
}
