package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "TransactionTypes")
public class TransactionType extends Audit {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public TransactionType() {
    }

    public TransactionType(String name, Timestamp createdAt, Timestamp updatedAt) {
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
