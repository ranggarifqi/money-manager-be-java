package com.ranggarifqi.moneymanager.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreateAccountDTO {

  @NotBlank(message = "accountType is mandatory")
  private String accountType;

  @NotBlank(message = "userId is mandatory")
  private String userId;

  @NotBlank(message = "name is mandatory")
  private String name;

  @NotBlank(message = "balance is mandatory")
  @Positive(message = "balance must be positive")
  private double balance;
}
