package com.example.bank.dao.repository;

import com.example.bank.domain.model.EmailData;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

  Optional<EmailData> findByEmail(String email);

  @Modifying
  @Transactional
  @Query("delete from EmailData e where e.user.id=:userId and e.email=:email")
  Integer deleteEmailByUserId(Long userId, String email);

  @Modifying
  @Transactional
  @Query("update EmailData e set e.email=:newEmail where e.user.id=:userId and e.email=:oldEmail")
  Integer updateEmailByUserId(Long userId, String newEmail, String oldEmail);
}
