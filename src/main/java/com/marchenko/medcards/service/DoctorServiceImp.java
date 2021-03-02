package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional
public class DoctorServiceImp implements DoctorService {

    private final DoctorRepository doctorRepository;

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
        Doctor doctor = doctorRepository.findByLogin(login);
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }

    @Override
    public Set<String> getAllSpecialization() {
        return doctorRepository.getAllSpecialization();
    }

    @Override
    public Set<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.getDoctorsBySpecialization(specialization);
    }

    @Override
    public Set<Doctor> getDoctorsBySurname(String surname) {
        return doctorRepository.getDoctorsBySurname(surname);
    }

    @Override
    public Set<Doctor> getDoctorsBySpecializationAndName(String specialization, String surname) {
        return doctorRepository.getDoctorsBySpecializationAndSurname(specialization,surname);
    }

}
