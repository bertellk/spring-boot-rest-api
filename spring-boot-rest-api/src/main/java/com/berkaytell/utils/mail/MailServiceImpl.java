package com.berkaytell.utils.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void sendMail(String[] to, String[] cc, String subject, String body) throws MessagingException {
        MailResult mailResult = getResult(to, subject, body, cc);
        mailSender.send(mailResult.message);
    }

    @Override
    @Async
    public void sendMail(String to, String[] cc, String subject, String body) throws MessagingException {
        MailResult mailResult = getResult(new String[]{to}, subject, body, cc);
        mailSender.send(mailResult.message);
    }

    private MailResult getResult(String[] to, String subject, String body, String[] cc, String[] bcc) throws MessagingException {
        if (to == null || subject == null || body == null) {
            throw new IllegalArgumentException("to, subject, body must not be null");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = getMimeMessageHelper(message, to, subject, body);

        if (cc != null && cc.length > 0)
            helper.setCc(cc);

        if (bcc != null && bcc.length > 0)
            helper.setBcc(bcc);

        return new MailResult(message, helper);
    }

    private MailResult getResult(String[] to, String subject, String body, String[] cc) throws MessagingException {
        return getResult(to, subject, body, cc, null);
    }

    private MimeMessageHelper getMimeMessageHelper(MimeMessage mimeMessage, String[] to, String subject, String body) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        return helper;
    }

    // Mail'e dosya ekler -> docx, pdf, excel, vb.
    private void addAttachments(MimeMessageHelper helper, File[] attachments) throws MessagingException {
        if (attachments != null) {
            for (File attachment : attachments) {
                helper.addAttachment(Objects.requireNonNull(attachment.getName()), attachment);
            }
        }
    }

    private record MailResult(MimeMessage message, MimeMessageHelper helper) {
    }
}
