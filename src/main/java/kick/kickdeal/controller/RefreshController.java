package kick.kickdeal.controller;

import jakarta.servlet.http.HttpServletResponse;
import kick.kickdeal.dto.RefreshDTO;
import kick.kickdeal.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public String refreshToken(@RequestBody RefreshDTO refreshDTO, HttpServletResponse response) {
        String refreshToken = refreshDTO.getRefreshToken();
        return refreshTokenService.refreshAccessToken(refreshToken, response);
    }
}
