package com.marchenko.medcards.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@NoArgsConstructor
@Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "patient_id")
    @ManyToOne
    private Patient patient;

    @Setter
    @JoinColumn(name = "doctor_id")
    @ManyToOne
    private Doctor doctor;
    @Setter
    @Column(name = "date_record")
    private LocalDateTime dateRecord;

    public Record(Patient patient, Doctor doctorId, LocalDateTime dateRecord) {
        this.patient = patient;
        this.doctor = doctorId;
        this.dateRecord = dateRecord;
    }
}
