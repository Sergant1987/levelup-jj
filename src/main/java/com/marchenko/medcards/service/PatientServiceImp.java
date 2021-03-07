package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Patient findByLogin(String login) {
        Patient patient = patientRepository.findByLogin(login);
        return patient;
    }

    @Override
    public List<Patient> findByNameOrSurnameOrPhone(String name, String surname, String phone) {
        if (name == null && surname == null && phone == null) {
            return Collections.EMPTY_LIST;
        }
        Specification<Patient> specification = Specification.where(new FilterPatient().getSpecificationPatient(name, surname, phone));
        return patientRepository.findAll(specification);
    }
}
