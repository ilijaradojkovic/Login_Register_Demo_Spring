package com.example.login_register_demo_spring.repository;

import com.example.login_register_demo_spring.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyTokenRepository extends JpaRepository<VerificationToken,String> {


}
