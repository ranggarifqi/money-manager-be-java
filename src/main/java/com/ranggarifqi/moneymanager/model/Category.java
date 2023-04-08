package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToOne
    @JoinColumn(name = "fk_Categories_Users")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_Categories_parent")
    private Category parent;

    @ManyToMany
    @JoinTable(
        name = "TransactionCategories",
        joinColumns = {@JoinColumn(name = "fk_TransactionCategories_Categories")},
        inverseJoinColumns = {@JoinColumn(name = "fk_TransactionCategories_Transactions")}
    )
    private Set<Transaction> transactions = new HashSet<Transaction>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
