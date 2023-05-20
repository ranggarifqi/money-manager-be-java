package com.ranggarifqi.moneymanager.account.dto;

import java.util.UUID;

public record CreateAccountResponse(UUID id, String accountType, String userId, String name, double balance) {
}
