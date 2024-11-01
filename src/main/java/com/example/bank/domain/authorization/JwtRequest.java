package com.example.bank.domain.authorization;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

  @NotEmpty
  private String email;

  @NotEmpty
  private String password;
}
