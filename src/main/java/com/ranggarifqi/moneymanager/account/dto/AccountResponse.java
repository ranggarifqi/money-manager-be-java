package com.ranggarifqi.moneymanager.account.dto;

import java.util.Date;

public record AccountResponse(String id, String userId, String accountType, String name, double balance, Date createdAt, Date updatedAt) {
}
