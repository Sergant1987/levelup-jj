package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.PatientForm;

import java.util.List;

public interface PatientService {
    Patient create(PatientForm form);

    Patient create(Patient patient);

    Patient findPatientById(Long id);

    Patient findPatientByLogin(String login);

    List<Patient> findPatientsByNameAndSurnameAndPhone(String name, String surname, String phone);
}
