package com.marchenko.medcards.repository;


import com.marchenko.medcards.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long>, JpaSpecificationExecutor<Doctor> {

//    Optional<Doctor> findByLoginasd(String login);
    Doctor findByLogin(String login);

}
