package com.example.login_register_demo_spring.listeners;

import com.example.login_register_demo_spring.events.ChangePasswordEvent;
import com.example.login_register_demo_spring.models.Password;
import com.example.login_register_demo_spring.service.ResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class PasswordChangedEventHandler implements ApplicationListener<ChangePasswordEvent> {

    private String serverUrl="http://localhost:8080";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResetTokenService passwordService;

    @Override
    public void onApplicationEvent(ChangePasswordEvent event) {
//create password token
        Password password=event.getPassword();
        String token= UUID.randomUUID().toString();
        passwordService.createResetToken(password,token);
        //get email property
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("Reset password");
        mailMessage.setTo(password.getEmail());
        mailMessage.setText(serverUrl+"/resetComplete?token="+token);
       mailSender.send(mailMessage);
        //send email
    }
}
