package com.example.bank.domain.dtos;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PhoneDataDto {

  @NotEmpty
  private String phone;
}
