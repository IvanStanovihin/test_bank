package com.example.bank.domain.dtos;


import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangeEmailDto {

  @NotEmpty
  private String oldEmail;

  @NotEmpty
  private String newEmail;
}
