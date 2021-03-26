package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatientRepository extends JpaRepository<Patient,Long>, JpaSpecificationExecutor<Patient> {
    Patient findPatientByLogin(String login);
}
