package com.example.ramenddang.member.controller;

import com.example.ramenddang.member.entity.UserRefresh;
import com.example.ramenddang.member.jwt.JWTUtil;
import com.example.ramenddang.member.repository.RefreshRepository;
import com.example.ramenddang.member.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReissueController {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final TokenService tokenService;

    public ReissueController(JWTUtil jwtUtil, RefreshRepository refreshRepository, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //refreshtoken cookie에 담기
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        //refresh가 없다면 에러반환
        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //refresh 검중
        try {
            jwtUtil.isExpired(refresh);

        } catch (ExpiredJwtException e) {
            //refresh 만료되었다면 재 인증 필요
            return new ResponseEntity<>("재 로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        //refresh 토큰 유효성 검사-----------------

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        //refresh가 아니라면 에러 반환
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Long userId = jwtUtil.getUserId(refresh);
        String userLoginId = jwtUtil.getUserLoginId(refresh);
        String role = jwtUtil.getRole(refresh);

        // Redis 캐시에서 리프레시 토큰 확인
        System.out.println("redis에서 refresh 가져왔어 !");
        UserRefresh cachedUserRefresh = tokenService.getUserRefresh(refresh);
        if (cachedUserRefresh == null) {
            return new ResponseEntity<>("refresh token not found", HttpStatus.BAD_REQUEST);
        }

        //저장한 정보를 통해 새로운 토큰 생성
        String newAccess = jwtUtil.createJwt("access", userId, userLoginId, role, 1200000L);
        System.out.println("새로운 토큰 발급");
        response.setHeader("Authorization", "Bearer " + newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
