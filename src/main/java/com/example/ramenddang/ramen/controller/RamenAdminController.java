package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.dto.UpdateRamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/ramen")
public class RamenAdminController {

    private final RamenService ramenService;
    private final RamenRepository ramenRepository;

    public RamenAdminController(RamenService ramenService, RamenRepository ramenRepository) {
        this.ramenService = ramenService;
        this.ramenRepository = ramenRepository;
    }

    //ramen 글쓰기
    @PostMapping("/write")
    public ResponseEntity<String> writeRamen(@RequestPart("ramenDTO") RamenDTO ramenDTO,
                                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos) {

        Ramen ramen = createRamenFromDTO(ramenDTO);

        ramenService.writeRamen(ramen, ramenPhotos);

        return ResponseEntity.ok("작성 성공하였습니다.");
    }

    //ramen 내용수정
    @PutMapping("/update/{ramenId}")
    public ResponseEntity<Ramen> updateRamen(@PathVariable Long ramenId, @RequestPart("updateRamenDTO")UpdateRamenDTO updateRamenDTO,
                                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos){

        Ramen currentRamen = ramenRepository.findByRamenId(ramenId);
        Ramen updateRamen = ramenService.updateRamen(currentRamen, updateRamenDTO, ramenPhotos);

        return ResponseEntity.ok(updateRamen);
    }

    //ramen 내용삭제
    //ramen isDeleted = true로 변경
    @PutMapping("/delete/{ramenId}")
    public ResponseEntity<String> deleteRamen(@PathVariable Long ramenId) {

        Ramen currentRamen = ramenRepository.findByRamenId(ramenId);

        boolean isDeleted = ramenService.deleteRamen(currentRamen);

        if (isDeleted) {
            return ResponseEntity.ok("삭제 성공하였습니다.");
        }
        return ResponseEntity.status(404).body("해당 정보를 찾지 못하였습니다.");
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
