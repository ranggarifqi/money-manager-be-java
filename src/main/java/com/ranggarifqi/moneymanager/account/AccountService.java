package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public Account create(CreateAccountDTO payload) {
    this.logger.info("Creating a new account " + payload.toString());

    User user = this.userRepository.getReferenceById(payload.getUserId());
    Account newAccount = new Account(payload.getAccountType(), payload.getName(), payload.getBalance());
    newAccount.setUser(user);

    this.accountRepository.create(newAccount);

    return newAccount;
  }
}
