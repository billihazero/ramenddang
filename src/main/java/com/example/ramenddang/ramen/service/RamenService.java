package com.example.ramenddang.ramen.service;

import com.example.ramenddang.ramen.dto.RamenDTO;
import com.example.ramenddang.ramen.dto.UpdateRamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.entity.RamenPhoto;
import com.example.ramenddang.ramen.repository.RamenPhotoRepository;
import com.example.ramenddang.ramen.repository.RamenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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

    public List<Ramen> gatAllRamen(){
        return ramenRepository.findByIsDeletedFalse();
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

    public Ramen getRamenDetail(int ramenId) {
        return ramenRepository.findByRamenIdAndIsDeletedFalse(ramenId)
                .orElseThrow(() -> new RuntimeException("Ramen not found or is deleted for id: " + ramenId));
    }

    public Ramen ramenUpdate(@PathVariable Long ramenId, UpdateRamenDTO updateRamenDTO, List<MultipartFile> ramenPhotos) {

        Ramen existingRamen = ramenRepository.findByRamenId(ramenId);

        existingRamen.setRamenName(updateRamenDTO.ramenName());
        existingRamen.setRamenMenu(updateRamenDTO.ramenMenu());
        existingRamen.setRamenContent(updateRamenDTO.ramenContent());
        existingRamen.setRamenState(updateRamenDTO.ramenState());
        existingRamen.setRamenCity(updateRamenDTO.ramenCity());
        existingRamen.setRamenAddress(updateRamenDTO.ramenAddress());

        ramenRepository.save(existingRamen);

        ramenPhotoService.updateRamenPhoto(existingRamen, ramenPhotos);

        return existingRamen;
    }


    public boolean ramenDelete(Long ramenId) {
        Ramen existingRamen = ramenRepository.findByRamenId(ramenId);
        existingRamen.setIsDeleted(true);
        ramenRepository.save(existingRamen);

        return true;
    }
}
