package com.example.ramenddang.mypage.repository;

import com.example.ramenddang.mypage.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
