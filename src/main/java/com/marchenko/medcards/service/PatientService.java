package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Patient;

public interface PatientService {
    Patient create(Patient doctor);

    Patient findById(Long id);

    Patient findByLogin(String login);
}
