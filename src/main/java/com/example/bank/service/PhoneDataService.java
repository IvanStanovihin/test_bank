package com.example.bank.service;

import com.example.bank.dao.repository.PhoneDataRepository;
import com.example.bank.dao.repository.UserRepository;
import com.example.bank.domain.dtos.ChangePhoneDto;
import com.example.bank.domain.model.PhoneData;
import com.example.bank.domain.model.User;
import com.example.bank.exception.EntityNotFoundException;
import com.example.bank.exception.PhoneAlreadyUsedException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneDataService {

  private final UserRepository userRepository;
  private final PhoneDataRepository phoneDataRepository;

  public Boolean deletePhone(Long userId, String phone) {
    log.info("Deleting phone: {} by userId: {}", phone, userId);
    return phoneDataRepository.deletePhoneByUserId(userId, phone) > 0;
  }

  public Boolean changePhone(Long userId, ChangePhoneDto changePhoneDto) {
    log.info("Changing phoneDto: {} for userId: {}", changePhoneDto, userId);
    return phoneDataRepository.updatePhoneByUserId(userId, changePhoneDto.getNewPhone(), changePhoneDto.getOldPhone()) > 0;
  }

  public PhoneData addPhone(Long userId, String phone) {
    log.info("Adding phone: {} by userId: {}", phone, userId);
    Optional<PhoneData> emailDataOptional = phoneDataRepository.findByPhone(phone);
    if (emailDataOptional.isPresent()) {
      throw new PhoneAlreadyUsedException(phone);
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(userId));
    PhoneData phoneData = PhoneData.builder()
        .phone(phone)
        .user(user)
        .build();
    return phoneDataRepository.save(phoneData);
  }

}
