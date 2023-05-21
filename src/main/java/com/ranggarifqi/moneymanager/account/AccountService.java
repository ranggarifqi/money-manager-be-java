package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.common.exception.ForbiddenException;
import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements IAccountService{

  Logger logger = LoggerFactory.getLogger(AccountService.class);

  private final IUserRepository userRepository;

  private final IAccountRepository accountRepository;

  @Autowired
  public AccountService(IUserRepository userRepository, IAccountRepository accountRepository) {
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Account> findByUserId(UUID userId) {
    return this.accountRepository.findByUserId(userId);
  }

  @Override
  public Account findById(String id, UUID ownerId) throws Exception {
    this.logger.info("findById: id=" + id + "; ownerId=" + ownerId.toString());

    Account account = this.accountRepository.findById(UUID.fromString(id));

    if (account == null) {
      throw new NotFoundException("Account with id " + id + " doesn't exist");
    }

    boolean isNotOwned = !account.getUser().getId().equals(ownerId);

    if (isNotOwned) {
      throw new ForbiddenException("You are not authorized to access this resource");
    }

    return account;
  }

  @Override
  public Account create(CreateAccountDTO payload, String userId) {
    this.logger.info("Creating a new account " + payload.toString());

    User user = this.userRepository.getReferenceById(UUID.fromString(userId));
    Account newAccount = new Account(payload.getAccountType(), payload.getName(), payload.getBalance());
    newAccount.setUser(user);

    this.accountRepository.create(newAccount);

    return newAccount;
  }
}
