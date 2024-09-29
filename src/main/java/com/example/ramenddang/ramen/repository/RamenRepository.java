package com.example.ramenddang.ramen.repository;

import com.example.ramenddang.ramen.entity.Ramen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RamenRepository extends JpaRepository<Ramen, Integer> {
    Ramen findByRamenId(Long ramenId);

    List<Ramen> findByIsDeletedFalse();

    Optional<Ramen> findByRamenIdAndIsDeletedFalse(int ramenId);
}


