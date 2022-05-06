package com.example.login_register_demo_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken {

    public static final int EXPIRATION=60*24;
    @Id
    private String token;
    private String username;
    private Date expiryDate;


    public Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE,expiryTimeInMinutes);
        return cal.getTime();
    }
}
