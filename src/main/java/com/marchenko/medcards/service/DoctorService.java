package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;

public interface DoctorService {

    Doctor create(Doctor doctor);

    Doctor findById(Long id);

    Doctor findByLogin(String login);


}
