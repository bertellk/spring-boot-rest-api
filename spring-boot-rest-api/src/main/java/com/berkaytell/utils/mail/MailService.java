package com.berkaytell.utils.mail;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(String[] to, String[] cc, String subject, String body) throws MessagingException;

    void sendMail(String to, String[] cc, String subject, String body) throws MessagingException;
}
