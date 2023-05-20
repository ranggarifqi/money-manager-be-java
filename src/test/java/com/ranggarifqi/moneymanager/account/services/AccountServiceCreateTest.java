package com.ranggarifqi.moneymanager.account.services;

import com.ranggarifqi.moneymanager.account.AccountService;
import com.ranggarifqi.moneymanager.account.IAccountRepository;
import com.ranggarifqi.moneymanager.account.IAccountService;
import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountServiceCreateTest {

  @Mock
  private IUserRepository userRepository;

  @Mock
  private IAccountRepository accountRepository;

  private IAccountService accountService;

  @BeforeEach
  void beforeEach() {
    accountService = new AccountService(this.userRepository, this.accountRepository);
  }

  @Test
  public void shouldCallAccountRepositoryCreate() {
    UUID dummyUserId = UUID.randomUUID();
    CreateAccountDTO payload = new CreateAccountDTO("Cash", dummyUserId.toString(), "some account type", 1000);

    Account result = this.accountService.create(payload);

    Mockito.verify(this.accountRepository, Mockito.times(1)).create(ArgumentMatchers.any());
    Assertions.assertNotNull(result);
  }

  @Test
  public void shouldGetTheReferenceOfUser() {
    UUID dummyUserId = UUID.randomUUID();
    CreateAccountDTO payload = new CreateAccountDTO("Cash", dummyUserId.toString(), "some account type", 1000);

    this.accountService.create(payload);

    Mockito.verify(this.userRepository, Mockito.times(1)).getReferenceById(dummyUserId.toString());
  }
}
