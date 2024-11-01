package com.example.bank.api.controllers;

import com.example.bank.domain.authorization.JwtRequest;
import com.example.bank.domain.authorization.JwtResponse;
import com.example.bank.domain.authorization.RefreshJwtRequest;
import com.example.bank.service.authorization.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Авторизация")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


  private final AuthService authService;

  @Operation(summary = "Авторизация пользователя по email+password")
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Validated @RequestBody JwtRequest authRequest) {
    final JwtResponse token = authService.login(authRequest);
    return ResponseEntity.ok(token);
  }

  @Operation(summary = "Обновление access токена")
  @PostMapping("/token")
  public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
    final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
    return ResponseEntity.ok(token);
  }

  @Operation(summary = "Обновление refresh токена")
  @PostMapping("/refresh")
  public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
    final JwtResponse token = authService.refresh(request.getRefreshToken());
    return ResponseEntity.ok(token);
  }
}
