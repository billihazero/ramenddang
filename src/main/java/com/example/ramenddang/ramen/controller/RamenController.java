package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.mypage.dto.ProfileDTO;
import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ramen")
public class RamenController {

    private final RamenService ramenService;

    public RamenController(RamenService ramenService) {
        this.ramenService = ramenService;
    }

    @PostMapping("/write")
    public ResponseEntity<String> ramenWrite(@RequestPart("ramenDTO") RamenDTO ramenDTO,
                                             @RequestPart("ramenPhotos")List<MultipartFile> ramenPhotos) {
        ramenService.ramenWrite(ramenDTO, ramenPhotos);

        return  ResponseEntity.ok("write success");
    }

}
