package com.example.bank.service;

import com.example.bank.dao.repository.AccountRepository;
import com.example.bank.domain.model.Account;
import com.example.bank.exception.EntityNotFoundException;
import com.example.bank.exception.IllegalAmountException;
import java.util.List;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  @Transactional
  @Scheduled(fixedRate = 30_000)
  public void increaseBalances() {
    log.info("Increasing balances");
    List<Account> accounts = accountRepository.findAll();
    accounts.forEach(this::increaseAccountsBalance);
  }

  @Transactional
  public boolean increaseAccountsBalance(Account account) {
    Float increasedBalance = (float) (account.getBalance() * 1.1);
    if (increasedBalance <= account.getBalanceLimit()) {
      account.setBalance(increasedBalance);
      log.info("Balance increased to: {} in account: {}", increasedBalance, account.getId());
      try {
        accountRepository.save(account);
      } catch (OptimisticLockException ex) {
        //Возможно добавление ретраев для подобных ситуаций
        log.error("Account: {} is currently locked", account.getId());
      }
      return true;
    } else {
      log.info("Balance limit reached for account: {}", account.getId());
      return false;
    }
  }

  @Transactional
  public boolean transferMoney(Long fromUserId, Long toUserId, float amount) {
    log.info("Transferring amount: {} money from: {} to: {}", amount, fromUserId, toUserId);
    Account accountSender = accountRepository.findByUserIdForWrite(fromUserId)
        .orElseThrow(() -> new EntityNotFoundException(fromUserId));
    Account accountReceiver = accountRepository.findByUserIdForWrite(toUserId)
        .orElseThrow(() -> new EntityNotFoundException(toUserId));
    if (amount <= 0) {
      log.error("Amount less than 0. Error while transfer from user: {} to user: {}, amount: {}", fromUserId, toUserId, amount);
      throw new IllegalAmountException(amount);
    }
    if (accountSender.getBalance() < amount) {
      log.error("On account: {} transferred amount: {} exceed current balance: {}", accountSender.getId(), amount,
          accountSender.getBalance());
      throw new IllegalAmountException(amount);
    }

    increaseBalance(accountReceiver, amount);
    decreaseBalance(accountSender, amount);
    log.info("Amount: {}, successfully transferred from user: {}, to user: {}", amount, fromUserId, toUserId);
    return true;
  }

  private Account decreaseBalance(Account account, Float amount) {
    Float newBalance = account.getBalance() - amount;
    account.setBalance(newBalance);
    return accountRepository.save(account);
  }

  private Account increaseBalance(Account account, Float amount) {
    Float newBalance = account.getBalance() + amount;
    account.setBalance(newBalance);
    return accountRepository.save(account);
  }
}
