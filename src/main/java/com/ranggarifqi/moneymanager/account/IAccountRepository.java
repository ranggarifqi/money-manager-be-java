package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.model.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountRepository {
  List<Account> findByUserId(UUID userId);

  Account findById(UUID id);

  void create(Account payload);
}
