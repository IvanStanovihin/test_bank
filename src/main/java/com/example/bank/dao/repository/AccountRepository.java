package com.example.bank.dao.repository;

import com.example.bank.domain.model.Account;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from Account a where a.user.id=:userId")
  Optional<Account> findByUserIdForWrite(Long userId);
}
