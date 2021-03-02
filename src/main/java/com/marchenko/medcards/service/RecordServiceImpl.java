package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import com.marchenko.medcards.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Reservation create(Patient patient, Doctor doctor, LocalDateTime date) {
        Reservation reservation = new Reservation(patient, doctor, date);
        return recordRepository.save(reservation);
    }

    @Override
    public List<Reservation> findRecordsByDoctorAndDate(Doctor doctor, LocalDateTime date) {
        return null;
    }

    @Override
    public List<Reservation> findRecordsByPatient(Patient patient) {
        return null;
    }
}
