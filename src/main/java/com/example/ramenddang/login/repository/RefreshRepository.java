package com.example.ramenddang.login.repository;

import com.example.ramenddang.login.entity.UserRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<UserRefresh, Long> {
    @Transactional
    void deleteByRefresh(String refresh);
}
