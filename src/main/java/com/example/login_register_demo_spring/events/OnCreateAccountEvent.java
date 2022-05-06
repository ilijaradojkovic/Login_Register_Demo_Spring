package com.example.login_register_demo_spring.events;

import com.example.login_register_demo_spring.models.Account;
import org.springframework.context.ApplicationEvent;


public class OnCreateAccountEvent extends ApplicationEvent {
    private String url;
            private Account account;

    public OnCreateAccountEvent(Account source,String url) {
        super(source);
        this.account=source;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
