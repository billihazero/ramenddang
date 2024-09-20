package com.example.ramenddang.member.repository;

import com.example.ramenddang.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
