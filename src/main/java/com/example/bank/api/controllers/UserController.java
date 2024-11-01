package com.example.bank.api.controllers;

import com.example.bank.domain.dtos.ChangeEmailDto;
import com.example.bank.domain.dtos.ChangePhoneDto;
import com.example.bank.domain.dtos.EmailDataDto;
import com.example.bank.domain.dtos.PhoneDataDto;
import com.example.bank.domain.dtos.UserDto;
import com.example.bank.domain.dtos.UserFilterDto;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.PhoneData;
import com.example.bank.domain.model.User;
import com.example.bank.service.PhoneDataService;
import com.example.bank.service.UserService;
import com.example.bank.service.authorization.AuthService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final PhoneDataService phoneDataService;
  private final AuthService authService;

  @PostMapping("/filter")
  public Page<UserDto> getUsers(
      @RequestBody UserFilterDto userFilterDto,
      @RequestParam(defaultValue = "1") Integer pageNumber,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    return userService.getUsers(userFilterDto, pageNumber, pageSize);
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/email")
  public EmailData addEmail(@RequestBody EmailDataDto emailDataDto) {
    return userService.addEmail(authService.getUserId(), emailDataDto.getEmail());
  }

  @PreAuthorize("hasAuthority('USER')")
  @DeleteMapping("/email")
  public boolean deleteEmail(@RequestBody EmailDataDto emailDataDto) {
    return userService.deleteEmail(authService.getUserId(), emailDataDto.getEmail());
  }

  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/email")
  public boolean changeEmail(@RequestBody ChangeEmailDto changeEmailDto) {
    return userService.changeEmail(authService.getUserId(), changeEmailDto);
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/phone")
  public PhoneData addPhone(@RequestBody PhoneDataDto phoneDataDto) {
    return phoneDataService.addPhone(authService.getUserId(), phoneDataDto.getPhone());
  }

  @PreAuthorize("hasAuthority('USER')")
  @DeleteMapping("/phone")
  public boolean deletePhone(@RequestBody PhoneDataDto phoneDataDto) {
    return phoneDataService.deletePhone(authService.getUserId(), phoneDataDto.getPhone());
  }

  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/phone")
  public boolean changePhone(@RequestBody ChangePhoneDto changePhoneDto) {
    return phoneDataService.changePhone(authService.getUserId(), changePhoneDto);
  }
}
