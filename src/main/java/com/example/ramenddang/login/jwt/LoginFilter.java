package com.example.ramenddang.login.jwt;

import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.login.entity.UserRefresh;
import com.example.ramenddang.login.repository.RefreshRepository;
import com.example.ramenddang.login.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final TokenService tokenService;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository, TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.tokenService = tokenService;
    }

    //사용자 인증
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // JSON 요청 본문을 Map으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);

            // Map에서 userLoginId와 userPasswd 추출
            String userLoginId = requestBody.get("userLoginId");
            String userPasswd = requestBody.get("userPasswd");

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userLoginId, userPasswd, null);

            // token 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("로그인 실패", e);
        }
    }

    //로그인 성공시 실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        // 삭제된 계정인지 확인
        if (memberDetails.isDeleted()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 삭제된 계정일 경우 로그인 차단
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("해당 ID는 삭제된 계정입니다.");
            return;
        }

        Long userId = memberDetails.getUserId();
        String userLoginId = memberDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("access",userId, userLoginId, role, 1200000L);
        String refresh = jwtUtil.createJwt("refresh",userId, userLoginId, role, 86400000L);

        //db에 저장
        addRefresh(userLoginId, refresh);

        //캐시에 저장
        tokenService.cacheUserRefresh(refresh);

        response.addHeader("Authorization", "Bearer " + access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

        // 응답 바디에 메시지 및 토큰 정보 포함
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // JSON 형식으로 응답 바디 생성
        String responseBody = String.format("{\"message\": \"로그인 성공하였습니다.\", \"access\": \"%s\", \"refresh\": \"%s\"}", access, refresh);

        // 응답 바디 출력
        response.getWriter().write(responseBody);

        // 상태 코드 200 OK 설정
        response.setStatus(HttpStatus.OK.value());

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        // 응답 바디에 메시지 정보 포함
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 인증 실패
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String responseBody = "{\"message\": \"비밀번호가 일치하지 않거나 존재하지 않는 계정입니다.\"}";
        response.getWriter().write(responseBody);
        
    }

    //refresh cookie 생성
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    //refresh DB 저장
    private void addRefresh(String userLoginId, String refresh) {

        Date date = new Date(System.currentTimeMillis() + 86400000L);

        UserRefresh userRefresh = new UserRefresh();
        userRefresh.setUserLoginId(userLoginId);
        userRefresh.setRefresh(refresh);
        userRefresh.setExpiration(date.toString());

        refreshRepository.save(userRefresh);
    }
}
