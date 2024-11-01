package com.example.bank.exception;

public class IllegalAmountException extends RuntimeException {

  public IllegalAmountException(Float amount) {
    super("Wrong value of amount: " + amount);
  }
}
