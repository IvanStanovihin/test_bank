package com.example.bank.domain.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDto {
  private Long id;
  private String name;
  private LocalDateTime dateOfBirth;
}
