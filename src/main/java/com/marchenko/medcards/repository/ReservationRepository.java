package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

   @Query("from Reservation r where r.doctor.id=:doctor_id and r.date =:date ")
    List<Reservation> findReservationsByDoctorIdAndDate(@Param("doctor_id") Long doctorId, @Param("date") LocalDate date);

    @Query("from Reservation r where r.patient.id=:patientId and r.date>=current_date ")
    List<Reservation> findByPatientIdWhenDateAfterNow(@Param("patientId") Long patientId);
}
