package com.example.ramenddang.ramen.service;

import com.example.ramenddang.exception.RamenNotFoundException;
import com.example.ramenddang.ramen.dto.UpdateRamenDTO;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import org.springframework.stereotype.Service;
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
    //isDeleted = false인 가게들만 출력
    public List<Ramen> gatAllRamen(){
        return ramenRepository.findByIsDeletedFalse();
    }

    //라멘가게 작성
    public void writeRamen(Ramen ramen, List<MultipartFile> ramenPhotos) {

        //db 저장
        ramenRepository.save(ramen);

        //라멘가게 사진 service 이동
        ramenPhotoService.uploadRamenPhoto(ramen, ramenPhotos);

    }

    //라멘가게 상세정보
    public Ramen getRamenDetail(int ramenId) {

        return ramenRepository.findByRamenIdAndIsDeletedFalse(ramenId)
                .orElseThrow(() -> new RamenNotFoundException("해당 라멘가게를 찾을 수 없습니다."));
    }

    //라멘가게 정보 수정
    public Ramen updateRamen(Ramen currentRamen, UpdateRamenDTO updateRamenDTO, List<MultipartFile> ramenPhotos) {
        
        //업데이트 된 라멘 정보 저장
        currentRamen.setRamenName(updateRamenDTO.ramenName());
        currentRamen.setRamenMenu(updateRamenDTO.ramenMenu());
        currentRamen.setRamenContent(updateRamenDTO.ramenContent());
        currentRamen.setRamenState(updateRamenDTO.ramenState());
        currentRamen.setRamenCity(updateRamenDTO.ramenCity());
        currentRamen.setRamenAddress(updateRamenDTO.ramenAddress());

        //db 저장
        ramenRepository.save(currentRamen);

        //라멘 가게 사진 service 이동
        ramenPhotoService.updateRamenPhoto(currentRamen, ramenPhotos);

        //업데이트 된 라멘가게 정보 전달
        return currentRamen;
    }
    
    //라멘 가게 isDeleted = true로 변경
    public boolean deleteRamen(Ramen currentRamen) {
        currentRamen.setIsDeleted(true);
        ramenRepository.save(currentRamen);

        return true;
    }
}
