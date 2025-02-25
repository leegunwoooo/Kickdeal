package kick.kickdeal.controller;

import kick.kickdeal.dto.CustomUserDetails;
import kick.kickdeal.dto.LoginDTO;
import kick.kickdeal.jwt.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public LoginDTO login(@RequestBody LoginDTO loginDTO) {
        // 인증을 위한 token 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getId(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 인증된 사용자 정보를 기반으로 JWT 생성
        String jwt = jwtUtil.createJwt(authentication.getName(), authentication.getAuthorities().toString(), 60 * 60 * 10L);

        // 로그인 응답에 JWT 포함
        loginDTO.setToken("Bearer " + jwt);
        return loginDTO;
    }
}
