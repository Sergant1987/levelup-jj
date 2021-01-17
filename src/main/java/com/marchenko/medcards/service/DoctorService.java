package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;

public interface DoctorService {

    Doctor create(Doctor doctor);

    Doctor getById(Long id);

    Doctor getByLogin(String login);

    String getPasswordByLogin(String login);

}
