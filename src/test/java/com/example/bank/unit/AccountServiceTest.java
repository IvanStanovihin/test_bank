package com.example.bank.unit;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.example.bank.dao.repository.AccountRepository;
import com.example.bank.domain.model.Account;
import com.example.bank.exception.IllegalAmountException;
import com.example.bank.service.AccountService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountService accountService;

  @Test
  public void transferAmountGreaterThanSenderBalance() {
    Account senderAccount = new Account(1L, null, 150F, 300F, 0L);
    Account receiverAccount = new Account(2L, null, 100F, 500F, 0L);

    given(accountRepository.findByUserIdForWrite(1L)).willReturn(Optional.of(senderAccount));
    given(accountRepository.findByUserIdForWrite(2L)).willReturn(Optional.of(receiverAccount));

    assertThrows(IllegalAmountException.class, () -> accountService.transferMoney(1L, 2L, 300));
    verify(accountRepository, never()).save(any());
  }

  @Test
  public void transferAmountLessThanNull() {
    Account senderAccount = new Account(1L, null, 150F, 300F, 0L);
    Account receiverAccount = new Account(2L, null, 100F, 500F, 0L);

    given(accountRepository.findByUserIdForWrite(1L)).willReturn(Optional.of(senderAccount));
    given(accountRepository.findByUserIdForWrite(2L)).willReturn(Optional.of(receiverAccount));

    assertThrows(IllegalAmountException.class, () -> accountService.transferMoney(1L, 2L, -10));
    verify(accountRepository, never()).save(any());
  }
}
