package com.marchenko.medcards.service;

import com.marchenko.medcards.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

  Reservation create (Patient patient, Doctor doctor, LocalDate date, TimeReservation time);

  Reservation findById(Long id);

  List<Reservation> findReservationsByDoctorIdAndDate(Long doctorId, LocalDate date);

  List<Reservation> findReservationsByPatientIdWhenDateAfterNow(Long patientId);



}

