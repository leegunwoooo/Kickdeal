package kick.kickdeal.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kick.kickdeal.dto.CustomUserDetails;
import kick.kickdeal.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 요청을 읽어 LoginDTO로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            LoginDTO loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

            // 인증을 위한 토큰 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());

            // AuthenticationManager를 통해 인증 수행
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청을 읽는 중 오류 발생", e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // JWT 토큰 생성
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

        // 헤더에 JWT 추가
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");

        // JSON 응답 반환
        try {
            response.getWriter().write("{\"token\": \"" + token + "\"}");
        } catch (IOException e) {
            throw new RuntimeException("토큰 응답을 생성하는 중 오류 발생", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        try {
            response.getWriter().write("{\"error\": \"인증 실패\"}");
        } catch (IOException e) {
            throw new RuntimeException("인증 실패 응답을 생성하는 중 오류 발생", e);
        }
    }
}

