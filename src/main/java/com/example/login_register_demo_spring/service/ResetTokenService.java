package com.example.login_register_demo_spring.service;

import com.example.login_register_demo_spring.models.Password;
import com.example.login_register_demo_spring.models.ResetToken;
import com.example.login_register_demo_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetTokenService {

    @Autowired
    private com.example.login_register_demo_spring.repository.ResetTokenRepository passwordRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public  void createResetToken(Password password, String token){
        ResetToken resetToken=new ResetToken(token,password.getEmail(),ResetToken.calculateExpiryDate(10),password.getUsername());
        passwordRepository.save(resetToken);

    }

    public boolean confirmResetToken(String token){
        if( token==null || token.isEmpty() || token.isBlank() ) return false;
        ResetToken resetToken=passwordRepository.getById(token);
        return true;
    }
    public void update(Password password){


        Optional<ResetToken> resetToken=passwordRepository.findById(password.getToken());

        if(resetToken.isPresent()){

            userRepository.updateUsersPassword(encoder.encode(password.getPassword()));
        }
    }
}
