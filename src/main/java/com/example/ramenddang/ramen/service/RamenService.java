package com.example.ramenddang.ramen.service;

import com.example.ramenddang.exception.RamenNotFoundException;
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
    private final RamenPhotoService ramenPhotoService;

    public RamenService(RamenRepository ramenRepository, RamenPhotoService ramenPhotoService) {
        this.ramenRepository = ramenRepository;
        this.ramenPhotoService = ramenPhotoService;
    }

    //라멘 리스트
    public List<Ramen> gatAllRamen(){
        return ramenRepository.findByIsDeletedFalse();
    }

    //라멘가게 작성
    public void ramenWrite(Ramen ramen, List<MultipartFile> ramenPhotos) {

        ramenRepository.save(ramen);

        ramenPhotoService.uploadRamenPhoto(ramen, ramenPhotos);

    }

    //라멘가게 상세정보
    public Ramen getRamenDetail(int ramenId) {

        return ramenRepository.findByRamenIdAndIsDeletedFalse(ramenId)
                .orElseThrow(() -> new RamenNotFoundException("해당 라멘가게를 찾을 수 없습니다."));
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
