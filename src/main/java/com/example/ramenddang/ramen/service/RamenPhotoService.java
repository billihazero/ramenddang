package com.example.ramenddang.ramen.service;

import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.entity.RamenPhoto;
import com.example.ramenddang.ramen.repository.RamenPhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    public void uploadRamenPhoto(Ramen ramen, List<MultipartFile> ramenPhotos){

        try{
            //각 이미지 파일에 대해 업로드 및 db 저장
            for (MultipartFile image : ramenPhotos) {

                //이미지 파일 경로 저장
                String dbFilePath = saveRamenPhoto(image, ramenPhotoDir);

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

    private String saveRamenPhoto(MultipartFile image, String dir) throws IOException {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        String filePath = ramenPhotoDir + fileName;
        String dbFilePath = ramenPhotoUrl + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }
}
