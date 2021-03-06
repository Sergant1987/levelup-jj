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
    List<Reservation> findByDoctorAndDate(@Param("doctor_id") Long doctorId, @Param("date") LocalDate date);

    List<Reservation> findByDoctorAndDate(Doctor doctor, LocalDate date);

    @Query("from Reservation r where r.doctor.id=:doctor_id and r.date >=:date ")
    List<Reservation> findByDoctorAndAfterDate(@Param("doctor_id") Long doctorId, @Param("date") LocalDate date);

    @Query("from Reservation r where r.patient=:patient and r.date>current_date ")
    List<Reservation> findByPatientAndDateAfterNow(@Param("patient") Patient patient);

    @Query("from Reservation r where r.patient.id=:patientId and r.date>current_date ")
    List<Reservation> findByPatientAndDateAfterNow(@Param("patientId") Long patientId);
}
