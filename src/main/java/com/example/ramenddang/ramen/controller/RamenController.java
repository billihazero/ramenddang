package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.dto.UpdateRamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ramen")
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

    //ramen 글쓰기
    @PostMapping("/write")
    public ResponseEntity<String> ramenWrite(@RequestPart("ramenDTO") RamenDTO ramenDTO,
                                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos) {

        ramenService.ramenWrite(ramenDTO, ramenPhotos);

        return  ResponseEntity.ok("write success");
    }

    //ramen 상세보기
    @GetMapping("/{ramenId}")
    public ResponseEntity<?> getRamenDetail(@PathVariable("ramenId") int ramenId) {

        try {
            Ramen ramen = ramenService.getRamenDetail(ramenId);
            return ResponseEntity.ok(ramen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //ramen 내용수정
    @PutMapping("/update/{ramenId}")
    public ResponseEntity<Ramen> ramenUpdate(@PathVariable Long ramenId, @RequestPart("updateRamenDTO")UpdateRamenDTO updateRamenDTO,
                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos){

        Ramen updateRamen = ramenService.ramenUpdate(ramenId, updateRamenDTO, ramenPhotos);

        return ResponseEntity.ok(updateRamen);
    }

    //ramen 내용삭제
    @PutMapping("/delete/{ramenId}")
    public ResponseEntity<String> ramenDelete(@PathVariable Long ramenId) {
        boolean isDeleted = ramenService.ramenDelete(ramenId);

        if (isDeleted) {
            return ResponseEntity.ok("delete success");
        }
        return ResponseEntity.status(404).body("not found");
    }
}
