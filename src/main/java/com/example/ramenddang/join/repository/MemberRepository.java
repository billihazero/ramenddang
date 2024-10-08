package com.example.ramenddang.join.repository;

import com.example.ramenddang.join.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(Long userId);


    Member findByUserLoginId(String userLoginId);

    boolean existsByUserLoginId(String userLoginId);
}
