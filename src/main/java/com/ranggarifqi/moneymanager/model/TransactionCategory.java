package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "`TransactionCategories`")
public class TransactionCategory extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`", nullable = false)
    private UUID id;

    @Column(name = "`transactionId`", nullable = false)
    private UUID transactionId;

    @Column(name = "`categoryId`", nullable = false)
    private UUID categoryId;

    public TransactionCategory() {
    }

    public TransactionCategory(UUID transactionId, UUID categoryId, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.transactionId = transactionId;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
