package com.example.ramenddang.member.repository;

import com.example.ramenddang.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserLoginId(String userLoginId);

}
