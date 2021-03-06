package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.models.TimeReservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

  Reservation create (Patient patient, Doctor doctor, LocalDate date, TimeReservation time);

  List<Reservation> findRecordsByDoctorAndDate(Long doctorId, LocalDate date);

  List<Reservation> findRecordsByPatient(Patient patient);

}
