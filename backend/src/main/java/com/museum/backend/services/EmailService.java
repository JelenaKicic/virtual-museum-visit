package com.museum.backend.services;

import java.io.File;

public interface EmailService {
    public void sendEmail(String subject, String recipient, String content, File attachment);
}
