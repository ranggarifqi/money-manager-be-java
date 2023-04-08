package com.ranggarifqi.moneymanager.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public class Audit {
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt", nullable = true)
    @LastModifiedDate
    private Date updatedAt;
}
