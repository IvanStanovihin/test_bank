package com.example.bank.domain.authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {

  public String refreshToken;

}
