package kick.kickdeal.service;

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

    public Boolean validateRefreshToken(String refreshToken) {
        return refreshTokenRepository.existsByRefresh(refreshToken);
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefresh(refreshToken);
    }
}
