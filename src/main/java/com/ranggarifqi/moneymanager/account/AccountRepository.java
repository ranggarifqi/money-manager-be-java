package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.model.Account;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository implements IAccountRepository{

  Logger logger = LoggerFactory.getLogger(AccountRepository.class);

  private final EntityManager entityManager;

  @Autowired
  public AccountRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void create(Account payload) {
    this.logger.info("Creating a new account " + payload.toString());

    this.entityManager.persist(payload);
  }
}
