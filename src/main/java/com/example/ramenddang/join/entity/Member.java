package com.example.ramenddang.join.entity;

import com.example.ramenddang.bookmark.entity.Bookmark;
import com.example.ramenddang.mypage.entity.Profile;
import com.example.ramenddang.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String userLoginId;
    private String userPasswd;
    private String userPhone;

    private String userName;
    private String userNickname;
    private String userEmail;

    private String userRole;

    private Boolean isDeleted;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //순환참고 해결
    private Profile profile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Bookmark> bookmarks = new ArrayList<>();

    public Member() {
    }

    //생성자를 통한 멤버 생성
    public Member(String userLoginId, String userPasswd,String userPhone, String userName, String userNickname, String userEmail, String userRole) {
        this.userLoginId = userLoginId;
        this.userPasswd = userPasswd;
        this.userPhone = userPhone;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.isDeleted = false;
    }

    public static Member createMember(String userLoginId, String userPasswd, String userPhone, String userName, String userNickname, String userEmail) {
        return new Member(userLoginId, userPasswd, userPhone, userName, userNickname, userEmail, "ROLE_USER");
    }

    public static Member createAdmin(String userLoginId, String userPasswd) {
        return new Member(userLoginId, userPasswd, null, null, "admin", null, "ROLE_ADMIN");
    }

}
