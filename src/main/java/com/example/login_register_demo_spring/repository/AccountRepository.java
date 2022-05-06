package com.example.login_register_demo_spring.repository;

import com.example.login_register_demo_spring.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

    public Account findByEmail(String email);

}
