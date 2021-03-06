package com.marchenko.medcards.service;


import com.marchenko.medcards.exceptions.NotFoundException;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.PatientForm;
import com.marchenko.medcards.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PatientServiceImp implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImp(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient create(PatientForm form) {
        return patientRepository.save(new Patient(form));
    }

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("Patient with id=%d doesn't exists",id)));
    }

    @Override
    public Patient findPatientByLogin(String login) {
        return patientRepository.findPatientByLogin(login);
    }

    @Override
    public List<Patient> findPatientsByNameAndSurnameAndPhone(String name, String surname, String phone) {
        if (paramIsEmpty(name, surname, phone)) {
            return Collections.emptyList();
        }
        Specification<Patient> specification =
                Specification.where(new FilterPatient().getSpecificationPatient(name, surname, phone));
        return patientRepository.findAll(specification);
    }

    private boolean paramIsEmpty(String name, String surname, String phone) {
        return (name == null || name.isBlank())
                && (surname == null || surname.isBlank())
                && (phone == null || phone.isBlank());
    }
}
