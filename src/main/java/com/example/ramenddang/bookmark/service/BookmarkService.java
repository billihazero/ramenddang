package com.example.ramenddang.bookmark.service;

import com.example.ramenddang.bookmark.entity.Bookmark;
import com.example.ramenddang.bookmark.repository.BookmarkRepository;
import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final RamenRepository ramenRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, MemberRepository memberRepository, RamenRepository ramenRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository = memberRepository;
        this.ramenRepository = ramenRepository;
    }

    public void addBookmark(Long ramenId, Long currentUserId) {

        //userId에 해당하는 member정보
        Member member = memberRepository.findByUserId(currentUserId);

        //ramen 정보
        Ramen ramen = ramenRepository.findByRamenId(ramenId);

        //bookmark 저장
        Bookmark bookmark = new Bookmark();
        bookmark.setRamen(ramen);
        bookmark.setMember(member);

        bookmarkRepository.save(bookmark);
    }

    public boolean deleteBookmark(Long ramenId, Long currentUserId) {

        try {
            Bookmark bookmark = bookmarkRepository.findByMember_UserIdAndRamen_RamenId(currentUserId, ramenId)
                    .orElseThrow(() -> new RuntimeException("Bookmark not found"));
            bookmarkRepository.delete(bookmark);
            return true; // 삭제 성공 시 true
        } catch (RuntimeException e) {
            return false; // 삭제할 북마크가 없을 경우 false
        }
    }

    public List<Ramen> getUserBookmarks(Long currentUserId) {
        //user가 북마크한 라멘. 유저 -> 북마크 -> 라멘
        return bookmarkRepository.findByMember_UserId(currentUserId).stream()
                .map(Bookmark::getRamen) // Bookmark 엔티티에서 Ramen 엔티티를 추출
                .collect(Collectors.toList());
    }
}
