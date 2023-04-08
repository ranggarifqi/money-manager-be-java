package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
public class Transaction extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "fromAccountId", nullable = false)
    private UUID fromAccountId;

    @Column(name = "toAccountId", nullable = true)
    private UUID toAccountId;

    @Column(name = "transactionType", nullable = false)
    private String transactionType;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amount", precision = 2, nullable = false)
    private double amount;

    @Column(name = "note", nullable = true)
    private String note;

    public Transaction() {
    }

    public Transaction(UUID fromAccountId, UUID toAccountId, String transactionType, Date date, double amount, String note, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transactionType = transactionType;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(UUID fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public UUID getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(UUID toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
