package com.example.bank.service.authorization;

import com.example.bank.dao.repository.UserRepository;
import com.example.bank.domain.authorization.JwtAuthentication;
import com.example.bank.domain.authorization.JwtRequest;
import com.example.bank.domain.authorization.JwtResponse;
import com.example.bank.domain.model.User;
import com.example.bank.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {


  private final UserRepository userRepository;
  private final Map<String, String> refreshStorage = new HashMap<>();
  private final JwtValidator jwtValidator;

  public JwtResponse login(JwtRequest authRequest) {
    final User user = userRepository.findByEmail(authRequest.getEmail())
        .orElseThrow(() -> new UserNotFoundException(authRequest.getEmail()));
    if (user.getPassword().equals(authRequest.getPassword())) {
      final String accessToken = jwtValidator.generateAccessToken(user, authRequest.getEmail());
      final String refreshToken = jwtValidator.generateRefreshToken(user, authRequest.getEmail());
      refreshStorage.put(authRequest.getEmail(), refreshToken);
      return new JwtResponse(accessToken, refreshToken);
    } else {
      throw new RuntimeException("Wrong password");
    }
  }

  public JwtResponse getAccessToken(String refreshToken) {
    if (jwtValidator.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtValidator.getRefreshClaims(refreshToken);
      final String email = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(email);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));
        final String accessToken = jwtValidator.generateAccessToken(user, email);
        return new JwtResponse(accessToken, null);
      }
    }
    return new JwtResponse(null, null);
  }

  public JwtResponse refresh(String refreshToken) {
    if (jwtValidator.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtValidator.getRefreshClaims(refreshToken);
      final String email = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(email);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));
        final String accessToken = jwtValidator.generateAccessToken(user, email);
        final String newRefreshToken = jwtValidator.generateRefreshToken(user, email);
        refreshStorage.put(email, newRefreshToken);
        return new JwtResponse(accessToken, newRefreshToken);
      }
    }
    throw new RuntimeException("Invalid JWT token");
  }

  public Long getUserId() {
    return getAuthInfo().getUserId();
  }

  public JwtAuthentication getAuthInfo() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }
}
