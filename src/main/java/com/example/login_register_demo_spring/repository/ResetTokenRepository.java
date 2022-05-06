package com.example.login_register_demo_spring.repository;

import com.example.login_register_demo_spring.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken,String> {
}
