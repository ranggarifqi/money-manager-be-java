package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
public class Account extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "accountTypeId", nullable = false)
    private UUID accountTypeId;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "balance", precision = 2)
    private double balance;

    public Account() {
    }

    public Account(UUID accountTypeId, UUID userId, String name, double balance, Date createdAt, Date updatedAt) {
        super(createdAt, updatedAt);
        this.accountTypeId = accountTypeId;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(UUID accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
