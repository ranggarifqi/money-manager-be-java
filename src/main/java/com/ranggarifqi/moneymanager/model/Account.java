package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
public class Account extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "accountType", nullable = false)
    private String accountType;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "balance", precision = 2, nullable = false)
    private double balance = 0;

    @ManyToOne
    @JoinColumn(name = "fk_Accounts_Users")
    private User user;

    public Account() {
    }

    public Account(String accountType, UUID userId, String name, double balance, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.accountType = accountType;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public User getUser() {
        return user;
    }
}
