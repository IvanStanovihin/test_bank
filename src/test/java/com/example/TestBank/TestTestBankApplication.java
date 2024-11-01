package com.example.TestBank;

import com.example.bank.TestBankApplication;
import org.springframework.boot.SpringApplication;

public class TestTestBankApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestBankApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
