package com.rajeeb.indentityservice.repository;

import com.rajeeb.indentityservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity>    findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByResetPasswordToken(String token);
}
