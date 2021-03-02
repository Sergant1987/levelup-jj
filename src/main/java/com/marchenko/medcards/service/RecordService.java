package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordService {

  Reservation create (Patient patient, Doctor doctor, LocalDateTime date);

  List<Reservation> findRecordsByDoctorAndDate(Doctor doctor, LocalDateTime date);

  List<Reservation> findRecordsByPatient(Patient patient);

}
