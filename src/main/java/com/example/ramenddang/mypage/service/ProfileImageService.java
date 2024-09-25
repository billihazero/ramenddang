package com.example.ramenddang.mypage.service;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.mypage.dto.ProfileDTO;
import com.example.ramenddang.mypage.entity.Profile;
import com.example.ramenddang.mypage.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileImageService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    @Value("${profile.image.dir}")
    private String profilesDir;

    @Value("${profile.image.url}")
    private String profilesUrl;

    public ProfileImageService(ProfileRepository profileRepository, MemberRepository memberRepository) {
        this.profileRepository = profileRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveProfileImage(ProfileDTO profileDTO, Long userId) throws IOException {

        try {
            MultipartFile profileImg = profileDTO.profileImg();

            //파일 이름 생성
            String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + profileImg.getOriginalFilename();

            //실제 파일 저장될 경로
            String filePath = profilesDir + fileName;

            //db에 저장될 경로 문자열
            String dbFilePath = profilesUrl + fileName;

            //Path객체 생성
            Path path = Paths.get(filePath);

            //디렉토리 생성
            Files.createDirectories(path.getParent());

            //디렉토리에 파일 저장
            Files.write(path, profileImg.getBytes());

            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 userId를 가진 사용자가 존재하지 않습니다."));

            //멤버의 기존 profile
            Profile profile = member.getProfile();

            //profile 확인, profile이 존재한다면 삭제 후 새로 생성
            if(profile != null){

                //기존 프로필 이미지 삭제
                deleteOldProfileImage(profile.getProfileUrl());
            }else{

                //프로필 이미지 경로 업데이트
                profile = new Profile();
                profile.setMember(member);
                member.setProfile(profile);
            }

            profile.setProfileUrl(dbFilePath);

            profileRepository.save(profile);


            memberRepository.save(member);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 이미지 저장 중 오류가 발생했습니다.", e);
        }
    }
    private void deleteOldProfileImage(String profileUrl) {
        // 프로필 URL에서 파일 이름 추출
        String fileName = profileUrl.substring(profileUrl.lastIndexOf("/") + 1);
        Path path = Paths.get(profilesDir, fileName);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // 파일 삭제 실패 시 로깅 (필요 시 추가적인 처리)
            e.printStackTrace();
        }
    }
}
