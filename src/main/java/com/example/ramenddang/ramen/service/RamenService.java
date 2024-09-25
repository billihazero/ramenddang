package com.example.ramenddang.ramen.service;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.entity.RamenPhoto;
import com.example.ramenddang.ramen.repository.RamenPhotoRepository;
import com.example.ramenddang.ramen.repository.RamenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RamenService {

    private final RamenRepository ramenRepository;
    private final RamenPhotoRepository ramenPhotoRepository;
    private final RamenPhotoService ramenPhotoService;

    @Value("${ramen.image.dir}")
    private String ramenPhotoDir;

    @Value("${ramen.image.url}")
    private String ramenPhotoUrl;

    public RamenService(RamenRepository ramenRepository, RamenPhotoRepository ramenPhotoRepository, RamenPhotoService ramenPhotoService) {
        this.ramenRepository = ramenRepository;
        this.ramenPhotoRepository = ramenPhotoRepository;
        this.ramenPhotoService = ramenPhotoService;
    }

    public void ramenWrite(RamenDTO ramenDTO, List<MultipartFile> ramenPhotos) {

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

        ramenPhotoService.uploadRamenPhoto(ramen, ramenPhotos);

    }
}
