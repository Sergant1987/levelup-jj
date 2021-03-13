package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.DoctorForm;
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
    public Doctor create(DoctorForm form) {
        return doctorRepository.save(new Doctor(form));
    }

    @Override
    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Doctor findDoctorByLogin(String login) {
        return doctorRepository.findByLogin(login);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Set<String> findAllSpecialization() {
        return doctorRepository.findAllSpecialization();
    }

    @Override
    public Set<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctorRepository.findDoctorsBySpecialization(specialization);
    }

    @Override
    public Set<Doctor> findDoctorsBySurname(String surname) {
        return doctorRepository.findDoctorsBySurname(surname);
    }

    @Override
    public Set<Doctor> findDoctorsBySpecializationAndName(String specialization, String surname) {
        return doctorRepository.findDoctorsBySpecializationAndSurname(specialization, surname);
    }

}
