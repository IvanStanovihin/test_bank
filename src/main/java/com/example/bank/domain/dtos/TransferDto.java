package com.example.bank.domain.dtos;

import lombok.Data;

@Data
public class TransferDto {

  private Long toUserId;
  private Float amount;
}
