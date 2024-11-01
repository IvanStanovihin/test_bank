package com.example.bank.domain.dtos;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferDto {

  @NotNull
  private Long toUserId;

  @NotNull
  private Float amount;
}
