package com.example.bank.domain.dtos;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailDataDto {

  @NotEmpty
  String email;
}
