package com.marchenko.medcards;

import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootApplication
public class MedcardsApplication {
    @Autowired
   static DoctorRepository doctorRepository;

    public static void main(String[] args) {
        SpringApplication.run(MedcardsApplication.class, args);

    }

}
