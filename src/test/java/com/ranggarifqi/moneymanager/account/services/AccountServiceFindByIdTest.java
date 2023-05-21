package com.ranggarifqi.moneymanager.account.services;

import com.ranggarifqi.moneymanager.account.AccountService;
import com.ranggarifqi.moneymanager.account.IAccountRepository;
import com.ranggarifqi.moneymanager.account.IAccountService;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountServiceFindByIdTest {
  @Mock
  IUserRepository userRepository;

  @Mock
  IAccountRepository accountRepository;

  private IAccountService accountService;

  @BeforeEach
  void beforeEach() {
    accountService = new AccountService(this.userRepository, this.accountRepository);
  }

  @Test
  public void shouldThrowNotFoundExceptionIfNotFound() {
    UUID dummyID = UUID.randomUUID();
    Mockito.when(this.accountRepository.findById(dummyID)).thenReturn(null);

    Exception error = null;

    try {
      this.accountService.findById(dummyID.toString());
    } catch (Exception e) {
      error = e;
    }

    Assertions.assertNotNull(error);
    Assertions.assertEquals("Account with id " + dummyID.toString() + " doesn't exist", error.getMessage());
  }
}
