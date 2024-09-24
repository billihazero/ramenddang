package com.example.ramenddang.ramen.service;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import org.springframework.stereotype.Service;

@Service
public class RamenService {

    private final RamenRepository ramenRepository;

    public RamenService(RamenRepository ramenRepository) {
        this.ramenRepository = ramenRepository;
    }

    public void ramenWrite(RamenDTO ramenDTO) {

        String ramenName = ramenDTO.ramenName();
        String ramenMenu = ramenDTO.ramenMenu();
        String ramenContent = ramenDTO.ramenContent();
        String ramenState = ramenDTO.ramenState();
        String ramenCity = ramenDTO.ramenCity();
        String ramenAddress = ramenDTO.ramenAddress();

        Ramen ramen = new Ramen();
        ramen.setRamenName(ramenName);
        ramen.setRamenMenu(ramenMenu);
        ramen.setRamenContent(ramenContent);
        ramen.setRamenState(ramenState);
        ramen.setRamenCity(ramenCity);
        ramen.setRamenAddress(ramenAddress);

        ramenRepository.save(ramen);

    }
}
