package com.ranggarifqi.moneymanager.common.email;

public interface IEmailService {
    void sendSimpleEmail(String recipient, String subject, String body);
}
