package com.ranggarifqi.moneymanager.user.dto;

import java.util.UUID;

public record SignUpResponse(UUID id, String name, String email, String phone) {
}
