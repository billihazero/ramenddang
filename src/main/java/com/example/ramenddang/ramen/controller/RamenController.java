package com.example.ramenddang.ramen.controller;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.service.RamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ramen")
public class RamenController {

    private final RamenService ramenService;

    public RamenController(RamenService ramenService) {
        this.ramenService = ramenService;
    }

    @PostMapping("/write")
    public ResponseEntity<String> ramenWrite(@RequestBody RamenDTO ramenDTO) {
        ramenService.ramenWrite(ramenDTO);

        return  ResponseEntity.ok("write success");
    }
}
