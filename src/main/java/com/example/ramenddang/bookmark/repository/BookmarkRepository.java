package com.example.ramenddang.bookmark.repository;

import com.example.ramenddang.bookmark.entity.Bookmark;
import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.ramen.entity.Ramen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByMember_UserIdAndRamen_RamenId(Long currentUserId, Long ramenId);
    List<Bookmark> findByMember_UserId(Long userId);
}
