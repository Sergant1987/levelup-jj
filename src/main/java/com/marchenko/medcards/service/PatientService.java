package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Patient;

import java.util.List;

public interface PatientService {
    Patient create(Patient doctor);

    Patient findById(Long id);

    Patient findByLogin(String login);

    List<Patient> findByNameOrSurnameOrPhone(String name, String surname, String phone);
}
