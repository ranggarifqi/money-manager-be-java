package com.ranggarifqi.moneymanager.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateAccountDTO {

  @NotBlank(message = "name is mandatory")
  private String name;

  @NotBlank(message = "accountType is mandatory")
  private String accountType;

  @PositiveOrZero(message = "balance must be >= 0")
  private double balance;

  public CreateAccountDTO(String name, String accountType, double balance) {
    this.name = name;
    this.accountType = accountType;
    this.balance = balance;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "CreateAccountDTO{" +
            "name='" + name + '\'' +
            ", accountType='" + accountType + '\'' +
            ", balance=" + balance +
            '}';
  }
}
