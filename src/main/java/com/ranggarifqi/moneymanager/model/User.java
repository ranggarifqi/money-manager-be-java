package com.ranggarifqi.moneymanager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "`Users`")
public class User extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`email`", nullable = false)
    private String email;

    @Column(name = "`phone`", nullable = false)
    private String phone;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "`verifyToken`", nullable = true)
    private String verifyToken;

    @Column(name = "`verifiedAt`", nullable = true)
    private Timestamp verifiedAt;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<Account>();

    @OneToMany()
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Column(name="accessLevel", nullable = false)
    @ColumnDefault("ROLE_USER")
    private String accessLevel = "ROLE_USER";

    public User() {
        super();
    }

    public User(String name, String email, String phone, String password, String verifyToken, Timestamp createdAt, Timestamp updatedAt, Timestamp verifiedAt, String accessLevel) {
        super(createdAt, updatedAt);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.verifyToken = verifyToken;
        this.verifiedAt = verifiedAt;
        this.accessLevel = accessLevel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public Timestamp getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Timestamp verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", verifyToken='" + verifyToken + '\'' +
                ", verifiedAt=" + verifiedAt + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
