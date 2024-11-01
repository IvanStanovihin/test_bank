package com.example.bank.domain.dtos;

import lombok.Data;

@Data
public class ChangePhoneDto {
  private String oldPhone;
  private String newPhone;
}
