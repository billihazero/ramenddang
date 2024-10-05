package com.example.ramenddang.ramen.service;

import com.example.ramenddang.exception.FileUploadException;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.entity.RamenPhoto;
import com.example.ramenddang.ramen.repository.RamenPhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RamenPhotoService {

    private final RamenPhotoRepository ramenPhotoRepository;

    @Value("${ramen.image.dir}")
    private String ramenPhotoDir;

    @Value("${ramen.image.url}")
    private String ramenPhotoUrl;
    public RamenPhotoService(RamenPhotoRepository ramenPhotoRepository) {
        this.ramenPhotoRepository = ramenPhotoRepository;
    }

    //라멘가게 사진 업로드
    @Transactional
    public void uploadRamenPhoto(Ramen ramen, List<MultipartFile> ramenPhotos){

        try{
            //각 이미지 파일에 대해 업로드 및 db 저장
            for (MultipartFile image : ramenPhotos) {

                //이미지 파일 경로 저장
                String dbFilePath = saveRamenPhoto(image);

                RamenPhoto ramenPhoto = new RamenPhoto();
                ramenPhoto.setRamen(ramen);
                ramenPhoto.setOriginalName(image.getOriginalFilename());
                ramenPhoto.setPhotoUrl(dbFilePath);

                //db에 저장
                ramenPhotoRepository.save(ramenPhoto);
            }
        }catch (IOException e) {
                // 파일 저장 중 오류가 발생한 경우 처리
                e.printStackTrace();
        }

    }

    //라멘가게 사진 수정
    @Transactional
    public void updateRamenPhoto(Ramen existingRamen, List<MultipartFile> ramenPhotos) {

        //기존 사진 목록의 파일 이름 불러오기
        List<String> existingPhotosName = ramenPhotoRepository.findByRamen(existingRamen)
                .stream()
                .map(RamenPhoto::getOriginalName)
                .collect(Collectors.toList());

        //새로 업로드된 사진 파일 이름 추출
        List<String> newPhotosName = ramenPhotos.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());

        //기존 목록 중에서 업로드 되지 않은 사진 삭제
        List<RamenPhoto> DeletePhotos = ramenPhotoRepository.findByRamen(existingRamen)
                .stream()
                .filter(photo -> !newPhotosName.contains(photo.getOriginalName()))
                .collect(Collectors.toList());

        //기존 사진 삭제
        for (RamenPhoto photo : DeletePhotos) {
            deleteRamenPhoto(photo);
        }

        for(MultipartFile image : ramenPhotos){
            if(!existingPhotosName.contains(image.getOriginalFilename())){
                try{
                    // 새로 업로드된 사진 저장
                    String dbFilePath = saveRamenPhoto(image);

                    // 새로운 RamenPhoto 객체 생성 및 저장
                    RamenPhoto ramenPhoto = new RamenPhoto();
                    ramenPhoto.setRamen(existingRamen);
                    ramenPhoto.setOriginalName(image.getOriginalFilename());
                    ramenPhoto.setPhotoUrl(dbFilePath);

                    // DB에 저장
                    ramenPhotoRepository.save(ramenPhoto);

                } catch (IOException e) {
                        throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
                }

            }
        }
    }

    //업데이트 중 삭제하는 이미지
    public void deleteRamenPhoto(RamenPhoto photo){

        //db삭제
        ramenPhotoRepository.delete(photo);

        //파일 시스템에서 삭제
        String photoPath = ramenPhotoDir + photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/"));

        try {
            Path path = Paths.get(photoPath);
            Files.deleteIfExists(path); // 파일이 존재하면 삭제
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //사진 파일 저장 경로
    private String saveRamenPhoto(MultipartFile image) throws IOException {

        //고유값을 주기 위해서
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        String filePath = ramenPhotoDir + fileName;
        String dbFilePath = ramenPhotoUrl + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }


}
