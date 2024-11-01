package com.example.bank.api.controllers;

import com.example.bank.domain.dtos.ChangeEmailDto;
import com.example.bank.domain.dtos.ChangePhoneDto;
import com.example.bank.domain.dtos.EmailDataDto;
import com.example.bank.domain.dtos.PhoneDataDto;
import com.example.bank.domain.dtos.TransferDto;
import com.example.bank.domain.dtos.UserDto;
import com.example.bank.domain.dtos.UserFilterDto;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.PhoneData;
import com.example.bank.service.AccountService;
import com.example.bank.service.PhoneDataService;
import com.example.bank.service.UserService;
import com.example.bank.service.authorization.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи")
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final PhoneDataService phoneDataService;
  private final AuthService authService;
  private final AccountService accountService;

  @Operation(summary = "Получение списка пользователей с применением фильтров и пагинацией")
  @PostMapping("/filter")
  public Page<UserDto> getUsers(
      @RequestBody UserFilterDto userFilterDto,
      @RequestParam(defaultValue = "1") Integer pageNumber,
      @RequestParam(defaultValue = "10") Integer pageSize
  ) {
    return userService.getUsers(userFilterDto, pageNumber, pageSize);
  }

  @Operation(summary = "Добавление нового email для пользователя")
  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/email")
  public EmailData addEmail(@Validated @RequestBody EmailDataDto emailDataDto) {
    return userService.addEmail(authService.getUserId(), emailDataDto.getEmail());
  }

  @Operation(summary = "Удаление существующего email пользователя")
  @PreAuthorize("hasAuthority('USER')")
  @DeleteMapping("/email")
  public boolean deleteEmail(@Validated @RequestBody EmailDataDto emailDataDto) {
    return userService.deleteEmail(authService.getUserId(), emailDataDto.getEmail());
  }

  @Operation(summary = "Изменение существующего у пользователя email на новый")
  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/email")
  public boolean changeEmail(@Validated @RequestBody ChangeEmailDto changeEmailDto) {
    return userService.changeEmail(authService.getUserId(), changeEmailDto);
  }

  @Operation(summary = "Добавление телефона пользователю")
  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/phone")
  public PhoneData addPhone(@Validated @RequestBody PhoneDataDto phoneDataDto) {
    return phoneDataService.addPhone(authService.getUserId(), phoneDataDto.getPhone());
  }

  @Operation(summary = "Удаление телефона польщзователя")
  @PreAuthorize("hasAuthority('USER')")
  @DeleteMapping("/phone")
  public boolean deletePhone(@Validated @RequestBody PhoneDataDto phoneDataDto) {
    return phoneDataService.deletePhone(authService.getUserId(), phoneDataDto.getPhone());
  }

  @Operation(summary = "Изменение телефона пользователя")
  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/phone")
  public boolean changePhone(@Validated @RequestBody ChangePhoneDto changePhoneDto) {
    return phoneDataService.changePhone(authService.getUserId(), changePhoneDto);
  }

  @Operation(summary = "Перевод денег от авторизованного пользователя к указанному в параметрах запроса")
  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/transfer")
  public boolean transfer(@Validated @RequestBody TransferDto transferDto) {
    return accountService.transferMoney(authService.getUserId(), transferDto.getToUserId(), transferDto.getAmount());
  }
}
