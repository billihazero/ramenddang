package com.example.ramenddang.bookmark.controller;

import com.example.ramenddang.bookmark.service.BookmarkService;
import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.ramen.entity.Ramen;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        Long userId = memberDetails.getUserId();
        String userLoginId = authentication.getName();

        System.out.println("userId = " + userId + " userLoginId = " + userLoginId);

        return userId;

    }

    @PostMapping("/add/{ramenId}")
    public ResponseEntity<String> addBookmark(@PathVariable("ramenId") Long ramenId) {
        Long currentUserId = getCurrentUserId();
        bookmarkService.addBookmark(ramenId, currentUserId);

        return ResponseEntity.ok("bookmark added");
    }

    @DeleteMapping("/delete/{ramenId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable("ramenId") Long ramenId) {
        Long currentUserId = getCurrentUserId();
        boolean isDeleted = bookmarkService.deleteBookmark(ramenId, currentUserId);

        if(isDeleted){
            return ResponseEntity.ok("bookmark deleted");
        }else
            return ResponseEntity.status(404).body("bookmark not deleted");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Ramen>> getUserBookmarks(){
        Long currentUserId = getCurrentUserId();
        List<Ramen> bookmarkedRamen = bookmarkService.getUserBookmarks(currentUserId);

        return ResponseEntity.ok(bookmarkedRamen);
    }

}
