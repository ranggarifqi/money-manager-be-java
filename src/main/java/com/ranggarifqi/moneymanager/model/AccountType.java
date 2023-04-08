package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "AccountTypes")
public class AccountType extends Audit {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public AccountType() {
    }

    public AccountType(String name, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
