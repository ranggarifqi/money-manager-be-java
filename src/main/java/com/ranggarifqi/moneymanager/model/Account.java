package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "`Accounts`")
public class Account extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false)
    private UUID id;

    @Column(name = "`accountType`", nullable = false)
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "`userId`", nullable = false)
    private User user;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`balance`", precision = 2, scale = 14, nullable = false)
    private double balance = 0;

    public Account() {
    }

    public Account(String accountType, String name, double balance, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.accountType = accountType;
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
