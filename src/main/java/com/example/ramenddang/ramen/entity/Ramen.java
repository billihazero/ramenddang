package com.example.ramenddang.ramen.entity;

import com.example.ramenddang.bookmark.entity.Bookmark;
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
public class Ramen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ramenId;

    private String ramenName;
    private String ramenMenu;

    @Column(length = 3000)
    private String ramenContent;
    private String ramenState;
    private String ramenCity;
    private String ramenAddress;

    private Boolean isDeleted;
    
    //ramen 글이 사라지면 사진도 삭제되어야해
    @OneToMany(mappedBy = "ramen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //순환참고 해결
    private List<RamenPhoto> ramenPhotos = new ArrayList<>();

    //ramen 글이 사라지면 리뷰도 삭제되어야해
    @OneToMany(mappedBy = "ramen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //순환참고 해결
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "ramen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //순환참고 해결
    private List<Bookmark> bookmarks = new ArrayList<>();

    public Ramen(){

    }

    public Ramen(String ramenName, String ramenMenu, String ramenContent, String ramenState, String ramenCity, String ramenAddress, Boolean isDeleted) {
        this.ramenName = ramenName;
        this.ramenMenu = ramenMenu;
        this.ramenContent = ramenContent;
        this.ramenState = ramenState;
        this.ramenCity = ramenCity;
        this.ramenAddress = ramenAddress;
        this.isDeleted = false;

    }

    public static Ramen createRamen(String ramenName, String ramenMenu, String ramenContent, String ramenState, String ramenCity, String ramenAddress){
        return new Ramen(ramenName, ramenMenu, ramenContent, ramenState, ramenCity, ramenAddress, false);
    }

}
