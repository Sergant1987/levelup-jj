package com.marchenko.medcards.service;

import com.marchenko.medcards.models.Patient;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class FilterPatient {

    public Specification<Patient> getSpecificationPatient(String name, String surname, String phone) {
        return new Specification<Patient>() {
            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();
                if (name != null&&!name.isBlank()) {
                    System.out.println("1");
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), name)));
                }
                if (surname != null&&!surname.isBlank()) {
                    System.out.println("1");
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("surname"), surname)));
                }
                if (phone != null&&!phone.isBlank()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("phone"), phone)));
                }
                System.out.println(predicates.size());
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
