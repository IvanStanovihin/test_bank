package com.example.bank.domain.dtos;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePhoneDto {

  @NotEmpty
  private String oldPhone;

  @NotEmpty
  private String newPhone;
}
