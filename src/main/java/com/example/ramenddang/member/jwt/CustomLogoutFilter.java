package com.example.ramenddang.member.jwt;

import com.example.ramenddang.member.entity.UserRefresh;
import com.example.ramenddang.member.repository.RefreshRepository;
import com.example.ramenddang.member.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilter {


    private final JWTUtil jwtUtil;

    private final RefreshRepository refreshRepository;

    private final TokenService tokenService;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //path and method verify
        String requestUri = request.getRequestURI();

        //로그아웃 경로인지 확인
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();

        //post요청인지 확인
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("로그아웃 접근");

        //refresh 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        //refresh 비어있으면 에러
        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //만료시간 지나면 에러
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // refresh 아니면 에러
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //DB에 저장되어 있지 않으면 에러
        System.out.println("redis에서 refresh 가져왔어 !");
        UserRefresh cachedUserRefresh = tokenService.getUserRefresh(refresh);
        if (cachedUserRefresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        //로그아웃 진행

        //Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        //캐시에서도 삭제
        tokenService.evictUserRefresh(refresh);
        System.out.println("로그아웃 완료");

        //Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
