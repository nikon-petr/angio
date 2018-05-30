package com.angio.server.util.email;

public interface EmailService {
    void sendEmailChangeLink(String to, String fullName, String link);
}
