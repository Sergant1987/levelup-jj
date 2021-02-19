package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Record;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordService {

  Record create (Patient patient, Doctor doctor, LocalDateTime date);

  List<Record> findRecordsByDoctorAndDate(Doctor doctor, LocalDateTime date);

  List<Record> findRecordsByPatient(Patient patient);

}
