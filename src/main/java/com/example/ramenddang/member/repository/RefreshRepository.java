package com.example.ramenddang.member.repository;

import com.example.ramenddang.member.entity.UserRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<UserRefresh, Long> {
    @Transactional
    void deleteByRefresh(String refresh);
}
