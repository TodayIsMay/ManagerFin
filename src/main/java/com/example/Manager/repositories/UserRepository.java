package com.example.Manager.repositories;

import com.example.Manager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUsername(String username);

    Optional<User> findByUsername(String username);
}
