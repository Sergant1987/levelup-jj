package com.marchenko.medcards.repository;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

   @Query("from Reservation r where r.doctor.id=:doctor_id and r.date =:date ")
    List<Reservation> findReservationsByDoctorAndDate(@Param("doctor_id") Long doctorId, @Param("date") LocalDate date);

    List<Reservation> findReservationsByDoctorAndDate(Doctor doctor, LocalDate date);

//    @Query("from Reservation r where r.doctor.id=:doctor_id and r.date >=:date ")
//    List<Reservation> findReservationsByDoctorIdAndDateIsAfter(@Param("doctor_id") Long doctorId, @Param("date") LocalDate date);
  List<Reservation> findReservationsByDoctorIdAndDateIsAfter(Long doctorId, LocalDate date);

    @Query("from Reservation r where r.patient.id=:patientId and r.date>current_date ")
    List<Reservation> findByPatientIdAndDateAfterNow(@Param("patientId") Long patientId);
}
