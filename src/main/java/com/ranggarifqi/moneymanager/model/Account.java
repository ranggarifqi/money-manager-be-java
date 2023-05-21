package com.ranggarifqi.moneymanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "`Accounts`")
public class Account extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`", nullable = false)
    private UUID id;

    @Column(name = "`accountType`", nullable = false)
    private String accountType;

    @JsonBackReference(value = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`userId`", nullable = false)
    private User user;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`balance`", precision = 2, scale = 14, nullable = false)
    private double balance = 0;

    public Account() {
    }

    public Account(String accountType, String name, double balance) {
        this.accountType = accountType;
        this.name = name;
        this.balance = balance;
    }

    public Account(String accountType, String name, double balance, Date createdAt, Date updatedAt) {
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", user=" + user.getId() + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
