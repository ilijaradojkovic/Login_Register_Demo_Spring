package com.example.login_register_demo_spring.service;

import com.example.login_register_demo_spring.models.CusomUserSecurityObj;
import com.example.login_register_demo_spring.models.User;
import com.example.login_register_demo_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    public  boolean save(User user){
        if(user==null) return false;
        repository.save(user);
        return true;

    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = repository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new CusomUserSecurityObj(user);
       // return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getRoles(user.getRole()));

    }
    //dodavanjem custom obj mapirali smo usera za roles pa ovo nije potrebno

//    private Collection<? extends GrantedAuthority> getRoles(Role roles) {
//        return Collections.singleton(new SimpleGrantedAuthority(roles.getName()));
//    }

}
