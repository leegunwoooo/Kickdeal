package kick.kickdeal.service;

import kick.kickdeal.entity.RefreshToken;
import kick.kickdeal.jwt.JWTUtil;
import kick.kickdeal.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        refreshTokenRepository.findByRefresh(refreshToken)
                .ifPresent(token -> refreshTokenRepository.deleteByRefresh(token.getRefresh()));

        // 새로운 리프레시 토큰 저장
        RefreshToken token = new RefreshToken();
        token.setUsername(username);
        token.setRefresh(refreshToken);
        token.setExpiration(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(token);

        return refreshToken;
    }

    public String refreshAccessToken(String refreshToken) {
        // 리프레시 토큰을 DB에서 조회하여 유효한지 확인
        RefreshToken token = refreshTokenRepository.findByRefresh(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has expired");
        }

        // 리프레시 토큰이 유효하면 새로운 액세스 토큰 발급
        String username = token.getUsername();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = (String) authentication.getPrincipal();

        long expirationMs = 1000L * 60 * 30;  // 액세스 토큰 유효기간 30분
        return jwtUtil.createAccessToken(username, role, expirationMs);
    }

    public Boolean validateRefreshToken(String refreshToken) {
        return refreshTokenRepository.existsByRefresh(refreshToken);
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefresh(refreshToken);
    }
}
