package com.example.login_register_demo_spring.events;

import com.example.login_register_demo_spring.models.Password;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;



public class ChangePasswordEvent extends ApplicationEvent {
    private String ulr;
    private Password password;

    public ChangePasswordEvent(Password source, String url) {
        super(source);
        this.ulr = url;
        this.password = source;

    }

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
