package com.ranggarifqi.moneymanager.account.services;

import com.ranggarifqi.moneymanager.account.AccountService;
import com.ranggarifqi.moneymanager.account.IAccountRepository;
import com.ranggarifqi.moneymanager.account.IAccountService;
import com.ranggarifqi.moneymanager.common.exception.ForbiddenException;
import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.model.User;
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

  private final UUID dummyID = UUID.randomUUID();
  private final UUID dummyOwnerId = UUID.randomUUID();

  @BeforeEach
  void beforeEach() {
    accountService = new AccountService(this.userRepository, this.accountRepository);
  }

  @Test
  public void shouldThrowNotFoundExceptionIfNotFound() {
    Mockito.when(this.accountRepository.findById(this.dummyID)).thenReturn(null);

    Exception error = null;

    try {
      this.accountService.findById(this.dummyID.toString(), this.dummyOwnerId);
    } catch (Exception e) {
      error = e;
    }

    Assertions.assertNotNull(error);
    Assertions.assertInstanceOf(NotFoundException.class, error);
    Assertions.assertEquals("Account with id " + dummyID.toString() + " doesn't exist", error.getMessage());
  }

  @Test
  public void shouldThrowForbiddenExceptionIfDoesNotBelongToOwner() {
    User dummyUser = new User();
    dummyUser.setId(UUID.randomUUID());

    Account account = new Account();
    account.setId(this.dummyID);
    account.setUser(dummyUser);

    Mockito.when(this.accountRepository.findById(this.dummyID)).thenReturn(account);

    Exception error = null;

    try {
      this.accountService.findById(this.dummyID.toString(), this.dummyOwnerId);
    } catch (Exception e) {
      error = e;
    }

    Assertions.assertNotNull(error);
    Assertions.assertInstanceOf(ForbiddenException.class, error);
    Assertions.assertEquals("You are not authorized to access this resource", error.getMessage());
  }

  @Test
  public void shouldReturnAccountSuccessfully() {
    User dummyUser = new User();
    dummyUser.setId(this.dummyOwnerId);

    Account account = new Account();
    account.setId(this.dummyID);
    account.setUser(dummyUser);

    Mockito.when(this.accountRepository.findById(this.dummyID)).thenReturn(account);

    Exception error = null;
    Account result = null;

    try {
      result = this.accountService.findById(this.dummyID.toString(), this.dummyOwnerId);
    } catch (Exception e) {
      error = e;
    }

    Assertions.assertNull(error);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(account.getId(), result.getId());
    Assertions.assertEquals(account.getUser().getId(), result.getUser().getId());
  }
}
