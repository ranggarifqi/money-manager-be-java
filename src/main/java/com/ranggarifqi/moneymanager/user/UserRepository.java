package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements IUserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByEmail(String email) {
        this.logger.info("Finding user with email = " + email);
        TypedQuery<User> query = this.entityManager.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public User findByVerifyToken(String token) {
        this.logger.info("Finding user with verifyToken = " + token);
        TypedQuery<User> query = this.entityManager.createQuery("FROM User WHERE verifyToken = :verifyToken", User.class);
        query.setParameter("verifyToken", token);

        List<User> users = query.getResultList();

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public User getReferenceById(UUID userId) {
        this.logger.info("Getting user reference by id " + userId);
        return this.entityManager.getReference(User.class, userId);
    }

    @Transactional
    @Override
    public void create(User payload) {
        this.entityManager.persist(payload);
    }

    @Transactional
    @Override
    public void update(User payload) {
        this.entityManager.merge(payload);
    }
}
