package com.example.bank.exception;

public class PhoneAlreadyUsedException extends RuntimeException {

  public PhoneAlreadyUsedException(String phone) {
    super("Phone: " + phone + " already used");
  }
}
