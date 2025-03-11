package kick.kickdeal.service;

import jakarta.servlet.http.HttpServletResponse;
import kick.kickdeal.entity.RefreshToken;
import kick.kickdeal.jwt.JWTUtil;
import kick.kickdeal.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public String createRefreshToken(String username, String role) {

        long expirationMs = 1000L * 60 * 60 * 24 * 7;

        String refreshToken = jwtUtil.createRefreshToken(username, role, expirationMs);

        // 기존 refresh token이 존재하면 삭제
        refreshTokenRepository.findByRefresh(username)
                .ifPresent(token -> refreshTokenRepository.deleteByRefresh(refreshToken));

        // 새로운 리프레시 토큰 저장
        RefreshToken token = new RefreshToken();
        token.setUsername(username);
        token.setRefresh(refreshToken);
        token.setExpiration(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(token);

        return refreshToken;
    }

    public String refreshAccessToken(String refreshToken, HttpServletResponse response) {
        RefreshToken token = refreshTokenRepository.findByRefresh(refreshToken)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 Refresh Token"));

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("만료된 Refresh Token");
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        long expirationMs = 1000L * 60 * 30;  // 액세스 토큰 유효기간 30분
        String accessToken = jwtUtil.createAccessToken(username, role, expirationMs);

        response.addHeader("Authorization", "Bearer " + accessToken);
        return accessToken;
    }

}
