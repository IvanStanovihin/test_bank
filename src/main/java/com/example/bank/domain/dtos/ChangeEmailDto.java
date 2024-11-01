package com.example.bank.domain.dtos;

import lombok.Data;

@Data
public class ChangeEmailDto {
  private String oldEmail;
  private String newEmail;
}
