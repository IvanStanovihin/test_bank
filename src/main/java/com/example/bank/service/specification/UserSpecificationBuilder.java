package com.example.bank.service.specification;

import com.example.bank.domain.dtos.UserFilterDto;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.PhoneData;
import com.example.bank.domain.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder {

  public Specification<User> getUsers(UserFilterDto filter) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getDateOfBirth() != null) {
        predicates.add(criteriaBuilder.greaterThan(root.get("dateOfBirth"), filter.getDateOfBirth()));
      }

      if (filter.getPhone() != null && !filter.getPhone().isEmpty()) {
        Join<User, PhoneData> phoneDataJoin = root.join("phonesData");
        predicates.add(criteriaBuilder.equal(phoneDataJoin.get("phone"), filter.getPhone()));
      }

      if (filter.getName() != null && !filter.getName().isEmpty()) {
        predicates.add(criteriaBuilder.like(root.get("name"), filter.getName() + "%"));
      }

      if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
        Join<User, EmailData> emailDataJoin = root.join("emailsData");
        predicates.add(criteriaBuilder.like(emailDataJoin.get("email"), filter.getEmail()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

}
