package com.myblogp3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String sub, String txt) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(to);
        sm.setSubject(sub);
        sm.setText(txt);
        javaMailSender.send(sm);

    }
}
