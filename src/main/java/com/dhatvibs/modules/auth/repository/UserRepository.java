package com.dhatvibs.modules.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.modules.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserCode(String userCode); 
    
    
    //Optional<User> findByResetToken(String resetToken);   //added
    
    Optional<User> findByResetOtp(String resetOtp);
}
