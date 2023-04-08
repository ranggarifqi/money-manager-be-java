package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.Date;

public class Audit {
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = true)
    @LastModifiedDate
    private Timestamp updatedAt;

    public Audit() {
    }

    public Audit(Timestamp createdAt, Timestamp updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
