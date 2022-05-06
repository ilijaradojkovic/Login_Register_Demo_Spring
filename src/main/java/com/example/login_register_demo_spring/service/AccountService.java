package com.example.login_register_demo_spring.service;

import com.example.login_register_demo_spring.models.Account;
import com.example.login_register_demo_spring.models.Role;
import com.example.login_register_demo_spring.models.User;
import com.example.login_register_demo_spring.models.VerificationToken;
import com.example.login_register_demo_spring.repository.AccountRepository;
import com.example.login_register_demo_spring.repository.MyTokenRepository;
import com.example.login_register_demo_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MyTokenRepository tokenRepository;
    public void CreateVerificationToken(Account account, String token){
VerificationToken Vtoken=new VerificationToken();
Vtoken.setToken(token);
Vtoken.setUsername(account.getEmail());
Vtoken.setExpiryDate(Vtoken.calculateExpiryDate(10));
tokenRepository.save(Vtoken);
    }
    public boolean ConfirmAccount(String token){

        //heavy
        //retrive token
        Optional<VerificationToken> verificationTokenFromDB=tokenRepository.findById(token);
        if(verificationTokenFromDB.isEmpty()) return false;
        VerificationToken  verificationToken=verificationTokenFromDB.get();
      //  if(!verificationToken.getExpiryDate().after(new Date())) return false;
      //get account
        Account account=accountRepository.findByEmail(verificationToken.getUsername());
        //create user and roles and user details
        User user=new User(UUID.randomUUID(),account.getFirstName(),account.getLastName(),account.getEmail(),account.getPassword(),new Role("Default"));
       // CusomUserSecurityObj userDetails=new CusomUserSecurityObj(user);
        userRepository.save(user);
        //delete from account account
        accountRepository.deleteById(account.getEmail());
        //delete from tokens
        tokenRepository.deleteById(token);

return true;
    }

    public void CreateAccount(Account account){
    accountRepository.save(account);
    }
}
