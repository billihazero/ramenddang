package com.example.ramenddang.join.controller;

import com.example.ramenddang.exception.ExistingMemberException;
import com.example.ramenddang.join.dto.JoinDTO;
import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.service.JoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private static final Logger log = LoggerFactory.getLogger(JoinController.class);
    private final JoinService joinService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinController(JoinService joinService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.joinService = joinService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /* 2024.10.02 리팩토링
    유효성 검사 로직 추가, 생성자 이용 로직 추가
    */
    
    //일반회원가입
    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody JoinDTO joinDTO) {

        //DTO로부터 받은 데이터로 Member 객체 생성
        Member member = createMemberFromDTO(joinDTO);

        //동일한 id가 존재하는지 유효성 검사
        try {
            validateId(member.getUserLoginId());
        }catch(ExistingMemberException e) {
            return ResponseEntity.badRequest().body(member.getUserLoginId() + ": 이미 등록된 ID 입니다.");
        }

        String userPhone = joinDTO.userPhone();
        //입력한 전화번호가 없거나, 비어있다면 400에러 응답을 반환한다.
        if(userPhone==null || userPhone.isEmpty()) {
            return new ResponseEntity<>("전화번호를 입력해야합니다.", HttpStatus.BAD_REQUEST);
        }

        String userName = joinDTO.userName();
        //입력한 name이 없거나, 비어있다면 400에러 응답을 반환한다.
        if(userName==null || userName.isEmpty()) {
            return new ResponseEntity<>("이름을 입력해야합니다.", HttpStatus.BAD_REQUEST);
        }

        String userNickname = joinDTO.userNickname();
        //입력한 nickName이 없거나 비어있다면 400에러 응답을 반환한다.
        if(userNickname==null || userNickname.isEmpty()) {
            return new ResponseEntity<>("닉네임을 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        String userEmail = joinDTO.userEmail();
        //입력한 email이 없거나 비어있다면 400에러 응답을 반환한다.
        if(userEmail==null || userEmail.isEmpty()) {
            return new ResponseEntity<>("이메일을 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        joinService.joinMember(member);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
    }
    
    //관리자 회원가입
    @PostMapping("/admin/join")
    public ResponseEntity<String> joinAdmin(@RequestBody JoinDTO joinDTO) {
        //DTO로부터 받은 데이터로 Member 객체 생성
        Member member = createAdminFromDTO(joinDTO);

        //동일한 id가 존재하는지 유효성 검사
        try {
            validateId(member.getUserLoginId());
        }catch(ExistingMemberException e) {
            return ResponseEntity.badRequest().body(member.getUserLoginId() + ": 이미 등록된 ID 입니다.");
        }

        joinService.joinAdmin(member);

        return ResponseEntity.status(HttpStatus.CREATED).body("관리자 회원가입이 성공적으로 완료되었습니다.");
    }

    //아이디 중복확인 api
    @PostMapping("/checkid")
    public ResponseEntity<String> checkId(@RequestBody JoinDTO joinDTO){
        boolean isIdValid = joinService.checkId(joinDTO.userLoginId());

        if(isIdValid){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 아이디 입니다."); // 409 Conflict
        }
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    //아이디 중복확인 메소드
    private void validateId(String userLoginId) {
        boolean isExistMember = joinService.checkId(userLoginId);

        if(isExistMember) {
            log.error("이미 등록된 id 입니다.");
            throw new ExistingMemberException();
        }
    }

    //DTO 통한 Member 객체 생성
    private Member createMemberFromDTO(JoinDTO joinDTO) {

        return Member.createMember(joinDTO.userLoginId(),
                bCryptPasswordEncoder.encode(joinDTO.userPasswd()),
                joinDTO.userPhone(),
                joinDTO.userName(),
                joinDTO.userNickname(),
                joinDTO.userEmail()
        );
    }

    //DTO 통한 Admin 객체 생성
    private Member createAdminFromDTO(JoinDTO joinDTO){
        return Member.createAdmin(joinDTO.userLoginId(),
                bCryptPasswordEncoder.encode(joinDTO.userPasswd())
        );
    }

}
