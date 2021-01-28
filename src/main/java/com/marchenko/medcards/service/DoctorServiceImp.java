package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DoctorServiceImp implements DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImp(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public  Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Doctor findByLogin(String login) {
        Doctor doctor = doctorRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

}
