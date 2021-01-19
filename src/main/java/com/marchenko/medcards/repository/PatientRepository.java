package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatientRepository extends JpaRepository<Doctor,Long>, JpaSpecificationExecutor<Doctor> {

}
