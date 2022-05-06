package com.example.login_register_demo_spring.listeners;

import com.example.login_register_demo_spring.events.OnCreateAccountEvent;
import com.example.login_register_demo_spring.models.Account;
import com.example.login_register_demo_spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class AccountListener implements ApplicationListener<OnCreateAccountEvent> {

    //should be from application properties
    private String serverURL="http://localhost:8080/";

    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private AccountService accountService;

    @Override
    public void onApplicationEvent(OnCreateAccountEvent event) {
        System.out.println("cao");
        confirmCreateAccount(event);
    }

    private void confirmCreateAccount(OnCreateAccountEvent event) {
        Account acc= event.getAccount();
        String randomToken= UUID.randomUUID().toString();
        accountService.CreateVerificationToken(acc,randomToken);
        String recipientAddress=acc.getEmail();
        String subject="Account Confirmation";
        String confirmation_Url= event.getUrl() + "/accountConfirm?token="+randomToken;
        String message="Please confirm:";

        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmation_Url);

        mailSender.send(email);
    }
}
