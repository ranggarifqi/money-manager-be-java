package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.model.Account;

public interface IAccountService {
  Account create(CreateAccountDTO payload);
}
