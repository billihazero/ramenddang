package com.example.ramenddang.login.jwt;

import com.example.ramenddang.login.entity.UserRefresh;
import com.example.ramenddang.login.repository.RefreshRepository;
import com.example.ramenddang.login.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

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
        try {
            //path and method verify
            String requestUri = request.getRequestURI();

            // 로그아웃 경로인지 확인
            if (!requestUri.matches("^\\/logout$")) {
                filterChain.doFilter(request, response);
                return;
            }

            String requestMethod = request.getMethod();

            // POST 요청인지 확인
            if (!requestMethod.equals("POST")) {
                filterChain.doFilter(request, response);
                return;
            }

            // refresh 토큰 가져오기
            String refresh = null;
            Cookie[] cookies = request.getCookies();

            // 쿠키 배열이 null인지 확인
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refresh".equals(cookie.getName())) {
                        refresh = cookie.getValue();
                    }
                }
            }

            // 응답 바디에 메시지 정보 포함
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // refresh 비어있으면 에러 처리
            if (refresh == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String responseBody = "{\"message\": \"refresh token이 존재하지 않습니다.\"}";
                response.getWriter().write(responseBody);
                return;
            }

            // refresh 토큰 만료 시간 확인
            try {
                jwtUtil.isExpired(refresh);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String responseBody = "{\"message\": \"refresh token이 만료되었습니다.\"}";
                response.getWriter().write(responseBody);
                return;
            }

            // refresh 아니면 에러 처리
            String category = jwtUtil.getCategory(refresh);
            if (!"refresh".equals(category)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String responseBody = "{\"message\": \"유효하지 않은 refresh token입니다.\"}";
                response.getWriter().write(responseBody);
                return;
            }

            // DB에 저장되어 있지 않으면 에러
            UserRefresh cachedUserRefresh = tokenService.getUserRefresh(refresh);
            if (cachedUserRefresh == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String responseBody = "{\"message\": \"refresh token이 존재하지 않습니다.\"}";
                response.getWriter().write(responseBody);
                return;
            }

            // 로그아웃 진행

            // Refresh 토큰 DB에서 제거
            refreshRepository.deleteByRefresh(refresh);

            // 캐시에서도 삭제
            tokenService.evictUserRefresh(refresh);

            // Refresh 토큰 Cookie 삭제
            Cookie cookie = new Cookie("refresh", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");

            response.addCookie(cookie);

            // 로그아웃 성공
            response.setStatus(HttpServletResponse.SC_OK);
            String responseBody = "{\"message\": \"로그아웃 성공하였습니다.\"}";
            response.getWriter().write(responseBody);

        } catch (Exception e) {
            // 예외 발생 시 에러 처리
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String responseBody = "{\"message\": \"서버에서 문제가 발생했습니다. 다시 시도해 주세요.\"}";
            response.getWriter().write(responseBody);
        }
    }

}
