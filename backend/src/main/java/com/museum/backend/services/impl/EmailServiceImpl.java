package com.museum.backend.services.impl;

import com.museum.backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String subject, String recipient, String content, File attachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@museum.com");
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);

            if(attachment != null) {
                FileSystemResource file = new FileSystemResource(attachment);
                helper.addAttachment(file.getFilename(), file);
            }
            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
