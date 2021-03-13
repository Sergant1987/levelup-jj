package com.marchenko.medcards.service;

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
    public Reservation create(Patient patient, Doctor doctor, ReservationForm reservationForm) {
        Reservation reservation = new Reservation(patient, doctor, reservationForm);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findReservationsByDoctorAndDate(Long doctorId, LocalDate date) {
        return reservationRepository.findReservationsByDoctorAndDate(doctorId, date);
    }

    @Override
    public List<Reservation> findReservationsByPatientAndDateAfterNow(Long patientId) {
        return reservationRepository.findByPatientIdAndDateAfterNow(patientId);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).get();
    }
}
