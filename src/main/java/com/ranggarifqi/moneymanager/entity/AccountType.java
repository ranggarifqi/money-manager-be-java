package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "AccountTypes")
public class AccountType extends Audit {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public AccountType() {
    }

    public AccountType(String name, Date createdAt, Date updatedAt) {
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
