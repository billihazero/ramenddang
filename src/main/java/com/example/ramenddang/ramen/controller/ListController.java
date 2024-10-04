package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ListController {
    private final RamenService ramenService;

    public ListController(RamenService ramenService) {
        this.ramenService = ramenService;
    }

    //ramenlist 불러오기
    @GetMapping("/list")
    public ResponseEntity<List<Ramen>> getAllRamen() {
        List<Ramen> ramenList = ramenService.gatAllRamen();
        return ResponseEntity.ok(ramenList);
    }
}
