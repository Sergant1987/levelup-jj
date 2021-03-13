package com.marchenko.medcards.repository;


import com.marchenko.medcards.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {


    Doctor findByLogin(String login);

    @Query("select distinct d.specialization from Doctor d")
    Set<String> findAllSpecialization();

    Set<Doctor>findDoctorsBySpecialization(String specialization);

    @Query("from Doctor d where d.surname like :surname")
    Set<Doctor> findDoctorsBySurname(String surname);

    @Query("from Doctor d where d.surname like :surname and d.specialization= :specialization")
    Set<Doctor> findDoctorsBySpecializationAndSurname(String specialization, String surname);
}
