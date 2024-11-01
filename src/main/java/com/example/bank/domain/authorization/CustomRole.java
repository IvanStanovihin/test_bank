package com.example.bank.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum CustomRole implements GrantedAuthority {

  USER("USER"),
  VISITOR("VISITOR"),
  ADMIN("ADMIN");

  private final String value;

  @Override
  public String getAuthority() {
    return value;
  }
}
