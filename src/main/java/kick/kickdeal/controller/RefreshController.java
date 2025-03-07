package kick.kickdeal.controller;

import kick.kickdeal.dto.RefreshDTO;
import kick.kickdeal.entity.RefreshToken;
import kick.kickdeal.jwt.JWTUtil;
import kick.kickdeal.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public String refreshToken(@RequestBody RefreshDTO refreshDTO) {
        String refreshToken = refreshDTO.getRefreshToken();
        return refreshTokenService.refreshAccessToken(refreshToken);
    }
}
