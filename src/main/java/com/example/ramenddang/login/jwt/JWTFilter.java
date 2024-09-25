package com.example.ramenddang.login.jwt;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.login.dto.MemberDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //access토큰의 유효성 검증

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰만 추출

        //Authorization 헤더 검증
        if (accessToken == null) {
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //access토큰 만료 여부 확인
        //만료되었다면 다음 필터로 넘기지 x
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            writer.print("accessToken이 만료되었습니다.");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("유효하지 않은 token입니다.");

            //response status code
            //만료되었다면, front단과 협의하여 code를 결정한다.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //토큰에서 loginId와 role 획득
        Long userId = jwtUtil.getUserId(accessToken);
        String userLoginId = jwtUtil.getUserLoginId(accessToken);
        String role = jwtUtil.getRole(accessToken);

        Member member = new Member();
        member.setUserId(userId);
        member.setUserLoginId(userLoginId);
        member.setUserRole(role);

        MemberDetails memberDetails = new MemberDetails(member);

        //토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
