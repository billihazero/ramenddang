package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.exception.RamenNotFoundException;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ramen")
public class RamenController {
    private final RamenService ramenService;

    public RamenController(RamenService ramenService) {
        this.ramenService = ramenService;
    }

    //ramenlist 불러오기
    @GetMapping("/list")
    public ResponseEntity<List<Ramen>> getAllRamen() {
        List<Ramen> ramenList = ramenService.gatAllRamen();
        return ResponseEntity.ok(ramenList);
    }

    //ramen 상세보기
    @GetMapping("/{ramenId}")
    public ResponseEntity<?> getRamenDetail(@PathVariable("ramenId") int ramenId) {

        try {
            Ramen ramen = ramenService.getRamenDetail(ramenId);
            return ResponseEntity.ok(ramen);
        } catch (RamenNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
