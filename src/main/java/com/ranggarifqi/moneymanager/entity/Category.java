package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Categories")
public class Category extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "parentId", nullable = true)
    private UUID parentId;

    @Column(name = "name", nullable = false)
    private String name;

    public Category() {
    }

    public Category(UUID parentId, String name, Timestamp createdAt, Timestamp updatedAt) {
        super(createdAt, updatedAt);
        this.parentId = parentId;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
