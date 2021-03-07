package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.models.TimeReservation;
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
    public List<Reservation> findByDoctorAndDate(Long doctorId, LocalDate date) {
        return reservationRepository.findByDoctorAndDate(doctorId,date);
    }

    @Override
    public List<Reservation> findByPatient(Patient patient) {
        return null;
    }

    @Override
    public List<Reservation> findByDoctorAndAfterDate(Long doctorId, LocalDate date) {
        return null;
    }

    @Override
    public List<Reservation> findByDoctorAndDate(Doctor doctor, LocalDate date) {
        return reservationRepository.findByDoctorAndDate(doctor,date);
    }

    @Override
    public List<Reservation> findByPatientAndDateAfterNow(Patient patient) {
        return reservationRepository.findByPatientAndDateAfterNow(patient);
    }

    @Override
    public List<Reservation> findByPatientAndDateAfterNow(Long patientId) {
        return reservationRepository.findByPatientAndDateAfterNow(patientId);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).get();
    }
}
