package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Record;
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
    public Record create(Patient patient, Doctor doctor, LocalDateTime date) {
        Record record = new Record(patient, doctor, date);
        return recordRepository.save(record);
    }

    @Override
    public List<Record> findRecordsByDoctorAndDate(Doctor doctor, LocalDateTime date) {
        return null;
    }

    @Override
    public List<Record> findRecordsByPatient(Patient patient) {
        return null;
    }
}
