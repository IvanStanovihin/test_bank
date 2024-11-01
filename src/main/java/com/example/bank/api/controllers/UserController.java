package com.example.bank.api.controllers;

import com.example.bank.domain.dtos.EmailDataDto;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.User;
import com.example.bank.service.UserService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<User> getUsers() {
    return Collections.emptyList();
  }

  @PostMapping("/email")
  public EmailData addEmail(@RequestBody EmailDataDto emailDataDto) {
    return userService.addEmail(1L, emailDataDto.getEmail());
  }


}
