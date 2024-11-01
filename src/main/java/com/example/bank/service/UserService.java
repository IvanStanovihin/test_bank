package com.example.bank.service;

import com.example.bank.dao.repository.EmailDataRepository;
import com.example.bank.dao.repository.UserRepository;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.User;
import com.example.bank.exception.EmailAlreadyUsedException;
import com.example.bank.exception.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final EmailDataRepository emailDataRepository;


  public Boolean deleteEmail(Long userId, String email) {
    log.info("Deleting email: {} by userId: {}", email, userId);
    return emailDataRepository.deleteEmailByUserId(userId, email) > 0;
  }

  public Boolean changeEmail(Long userId, String oldEmail, String newEmail) {
    log.info("Changing oldEmail: {} to newEmail: {} for userId: {}", oldEmail, newEmail, userId);
    return emailDataRepository.updateEmailByUserId(userId, oldEmail, newEmail) > 0;
  }

  public EmailData addEmail(Long userId, String email) {
    log.info("Adding email: {} by userId: {}", email, userId);
    Optional<EmailData> emailDataOptional = emailDataRepository.findByEmail(email);
    if (emailDataOptional.isPresent()) {
      throw new EmailAlreadyUsedException(email);
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(userId));
    EmailData emailData = EmailData.builder()
        .email(email)
        .user(user)
        .build();
    return emailDataRepository.save(emailData);
  }



}
