package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.DoctorForm;

import java.util.List;
import java.util.Set;

public interface DoctorService {

    Doctor create(DoctorForm form);
    Doctor create(Doctor doctor);

    Doctor findDoctorById(Long id);

    Doctor findDoctorByLogin(String login);

    List<Doctor> findAllDoctors();

    Set<String> findAllSpecialization();

    Set<Doctor> findDoctorsBySpecialization(String specialization);

    Set<Doctor> findDoctorsBySurname(String surname);

    Set<Doctor> findDoctorsBySpecializationAndSurname(String specialization, String surname);

}
