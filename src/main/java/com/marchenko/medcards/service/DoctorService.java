package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;

import java.util.List;
import java.util.Set;

public interface DoctorService {

    Doctor create(Doctor doctor);

    Doctor findById(Long id);

    Doctor findByLogin(String login);

    List<Doctor> findAll();

    boolean existsById(Long id);

    Set<String> getAllSpecialization();

}
