package com.example.login_register_demo_spring.repository;

import com.example.login_register_demo_spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
public User findByEmail(String email);

@Modifying
@Transactional
@Query(value = "UPDATE user SET password=:newpassword",nativeQuery = true)
public  void updateUsersPassword(@Param("newpassword") String newpassword);
}
