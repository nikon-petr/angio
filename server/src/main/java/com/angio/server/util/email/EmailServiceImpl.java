package com.angio.server.util.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailChangeLink(String to, String fullName, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Смена электронного адреса");
        String text = String.format(
                new StringBuilder()
                        .append("Здравствуйте, %s!\n\n")
                        .append("Поступил запрос на смену электронного адреса. ")
                        .append("Для подтверждения нажмите на ссылку ниже или проигноируйте это сообщение, если оно было отправлено ошибочно.\n\n")
                        .append("%s")
                        .toString(), fullName, link);
        message.setText(text);
        emailSender.send(message);
    }
}
