package com.example.bank.service.authorization;

import com.example.bank.domain.authorization.CustomRole;
import com.example.bank.domain.authorization.JwtAuthentication;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtParser extends GenericFilterBean {

  private static final String AUTHORIZATION = "Authorization";

  private final JwtValidator jwtValidator;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
      throws IOException, ServletException {
    final String token = getTokenFromRequest((HttpServletRequest) request);
    if (token != null && jwtValidator.validateAccessToken(token)) {
      final Claims claims = jwtValidator.getAccessClaims(token);
      final JwtAuthentication jwtInfoToken = generate(claims);
      jwtInfoToken.setAuthenticated(true);
      SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
    }
    fc.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String bearer = request.getHeader(AUTHORIZATION);
    if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }
    return null;
  }

  private JwtAuthentication generate(Claims claims) {
    final JwtAuthentication jwtInfoToken = new JwtAuthentication();
    jwtInfoToken.setRoles(getRoles(claims));
    jwtInfoToken.setUserId(claims.get("userId", Long.class));
    jwtInfoToken.setEmail(claims.getSubject());
    return jwtInfoToken;
  }

  private Set<CustomRole> getRoles(Claims claims) {
    final List<String> roles = claims.get("roles", List.class);
    return roles.stream()
        .map(CustomRole::valueOf)
        .collect(Collectors.toSet());
  }

}
