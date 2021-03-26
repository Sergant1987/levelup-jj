package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Patient;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class FilterPatient {

    public Specification<Patient> getSpecificationPatient(String name, String surname, String phone) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (name != null&&!name.isBlank()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+name.toLowerCase()+"%")));
            }
            if (surname != null&&!surname.isBlank()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%"+surname.toLowerCase()+"%")));
            }
            if (phone != null&&!phone.isBlank()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("phone"), phone)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
