package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.models.TimeReservation;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

  Reservation create (Patient patient, Doctor doctor, LocalDate date, TimeReservation time);

  List<Reservation> findByDoctorAndDate(Long doctorId, LocalDate date);

  List<Reservation> findByPatient(Patient patient);

  List<Reservation> findByDoctorAndAfterDate(Long doctorId, LocalDate date);

  List<Reservation> findByDoctorAndDate(Doctor doctor, LocalDate date);

  List<Reservation> findByPatientAndDateAfterNow(Patient patient);

  List<Reservation> findByPatientAndDateAfterNow(Long patientId);

}
