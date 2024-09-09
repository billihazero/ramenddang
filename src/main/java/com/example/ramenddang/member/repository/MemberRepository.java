package com.example.ramenddang.member.repository;

import com.example.ramenddang.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Boolean existsByMemberId(String memberId);
}
