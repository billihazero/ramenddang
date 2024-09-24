package com.example.ramenddang.ramen.repository;

import com.example.ramenddang.ramen.entity.Ramen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RamenRepository extends JpaRepository<Ramen, Integer> {
}
