package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {

    @Query("from Record r where r.doctor=:doctor and r.dateRecord=:date")
    List<Record> findRecordsByDoctorAndDate(@Param("doctor") Doctor doctor,@Param("date") LocalDateTime date);

    @Query("from Record r where r.patient=:patient")
    List<Record> findRecordsByPatient(@Param("patient") Patient patient);
}