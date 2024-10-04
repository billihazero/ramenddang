package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.exception.RamenNotFoundException;
import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.dto.UpdateRamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/ramen")
public class RamenAdminController {

    private final RamenService ramenService;

    public RamenAdminController(RamenService ramenService) {
        this.ramenService = ramenService;
    }

    //ramen 글쓰기
    @PostMapping("/write")
    public ResponseEntity<String> ramenWrite(@RequestPart("ramenDTO") RamenDTO ramenDTO,
                                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos) {

        Ramen ramen = createRamenFromDTO(ramenDTO);

        ramenService.ramenWrite(ramen, ramenPhotos);

        return ResponseEntity.ok("작성 성공하였습니다.");
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

    private Ramen createRamenFromDTO(RamenDTO ramenDTO) {
        return Ramen.createRamen(ramenDTO.ramenName(),
                ramenDTO.ramenMenu(),
                ramenDTO.ramenContent(),
                ramenDTO.ramenState(),
                ramenDTO.ramenCity(),
                ramenDTO.ramenAddress()
        );

    }
}
