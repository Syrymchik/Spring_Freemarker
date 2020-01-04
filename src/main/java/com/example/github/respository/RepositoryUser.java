package com.example.github.respository;

import com.example.github.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryUser extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
    boolean existsUserByEmail(String email);
}
