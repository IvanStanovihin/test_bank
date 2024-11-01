package com.example.bank.domain.dtos;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

  @NotNull
  private Long id;

  @NotEmpty
  private String name;

  @NotNull
  private LocalDateTime dateOfBirth;
}
