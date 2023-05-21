package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.account.dto.CreateAccountDTO;
import com.ranggarifqi.moneymanager.model.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {

  List<Account> findByUserId(UUID userId);

  Account findById(String id, UUID ownerId) throws Exception;

  Account create(CreateAccountDTO payload, String userId);
}
