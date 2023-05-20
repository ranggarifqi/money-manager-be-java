package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.model.Account;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountRepository implements IAccountRepository{

  Logger logger = LoggerFactory.getLogger(AccountRepository.class);

  private final EntityManager entityManager;

  public AccountRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void create(Account payload) {
    this.logger.info("Creating new account " + payload.toString());

    this.entityManager.persist(payload);
  }
}
