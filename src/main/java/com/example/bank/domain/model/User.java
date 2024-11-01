package com.example.bank.domain.model;

import com.example.bank.domain.authorization.CustomRole;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private LocalDateTime dateOfBirth;
  private String password;

  @CollectionTable
  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = CustomRole.class)
  private Set<CustomRole> roles;
}
