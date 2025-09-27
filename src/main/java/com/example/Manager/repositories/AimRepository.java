package com.example.Manager.repositories;

import com.example.Manager.entities.Aim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AimRepository extends JpaRepository<Aim, Integer> {
}