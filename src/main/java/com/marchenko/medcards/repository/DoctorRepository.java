package com.marchenko.medcards.repository;


import com.marchenko.medcards.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor,Long>, JpaSpecificationExecutor<Doctor> {


    Doctor findByLogin(String login);

    @Query("select distinct d.specialization from Doctor d")
    Set<String> getAllSpecialization();

}
