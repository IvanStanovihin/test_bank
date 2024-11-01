package com.example.bank.service;

import com.example.bank.dao.repository.EmailDataRepository;
import com.example.bank.dao.repository.PhoneDataRepository;
import com.example.bank.dao.repository.UserRepository;
import com.example.bank.domain.dtos.ChangeEmailDto;
import com.example.bank.domain.dtos.UserDto;
import com.example.bank.domain.dtos.UserFilterDto;
import com.example.bank.domain.mapper.UserMapper;
import com.example.bank.domain.model.EmailData;
import com.example.bank.domain.model.User;
import com.example.bank.exception.EmailAlreadyUsedException;
import com.example.bank.exception.EntityNotFoundException;
import com.example.bank.service.specification.UserSpecificationBuilder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserSpecificationBuilder userSpecificationBuilder;
  private final UserRepository userRepository;
  private final EmailDataRepository emailDataRepository;

  private final UserMapper userMapper;

  public Page<UserDto> getUsers(UserFilterDto userFilterDto, Integer pageNumber, Integer pageSize) {
    log.info("Getting user with params pageNumber: {}, pageSize: {}, filter: {}", pageNumber, pageSize, userFilterDto);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    Specification<User> searchSpecification = userSpecificationBuilder.getUsers(userFilterDto);
    return userRepository.findAll(searchSpecification, pageable).map(userMapper::toDto);
  }

  public Boolean deleteEmail(Long userId, String email) {
    log.info("Deleting email: {} by userId: {}", email, userId);
    return emailDataRepository.deleteEmailByUserId(userId, email) > 0;
  }

  public Boolean changeEmail(Long userId, ChangeEmailDto changeEmailDto) {
    log.info("Changing emailDto: {} for userId: {}", changeEmailDto, userId);
    return emailDataRepository.updateEmailByUserId(userId, changeEmailDto.getNewEmail(), changeEmailDto.getOldEmail()) > 0;
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
