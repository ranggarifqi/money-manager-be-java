package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = this.entityManager.createQuery("FROM User WHERE \"email\" = :email", User.class);
        query.setParameter("email", email);

        List<User> users = query.getResultList();

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Transactional
    @Override
    public void create(User payload) {
        this.entityManager.persist(payload);
    }
}
