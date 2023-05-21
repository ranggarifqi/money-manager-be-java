package com.ranggarifqi.moneymanager.account;

import com.ranggarifqi.moneymanager.model.Account;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AccountRepository implements IAccountRepository{

  Logger logger = LoggerFactory.getLogger(AccountRepository.class);

  private final EntityManager entityManager;

  private final IUserRepository userRepository;

  @Autowired
  public AccountRepository(EntityManager entityManager, IUserRepository userRepository) {
    this.entityManager = entityManager;
    this.userRepository = userRepository;
  }

  @Override
  public List<Account> findByUserId(UUID userId) {
    this.logger.info("Finding accounts by user Id " + userId.toString());

    String sql = "FROM Account where user = :user";

    User user = this.userRepository.getReferenceById(userId);

    TypedQuery<Account> query = this.entityManager.createQuery(sql, Account.class);
    query.setParameter("user", user);

    return query.getResultList();
  }

  @Override
  public Account findById(UUID id) {
    this.logger.info("Finding accounts by account Id " + id.toString());

    String sql = "FROM Account where id = :id";
    TypedQuery<Account> query = this.entityManager.createQuery(sql, Account.class);
    query.setParameter("id", id);

    List<Account> result = query.getResultList();

    if (result.size() == 0) {
      return null;
    }

    return result.get(0);
  }

  @Transactional
  @Override
  public void create(Account payload) {
    this.logger.info("Inserting a new account " + payload.toString());

    this.entityManager.persist(payload);
  }
}
