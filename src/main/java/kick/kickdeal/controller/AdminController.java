package kick.kickdeal.controller;

import lombok.RequiredArgsConstructor;
import kick.kickdeal.entity.Role;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @PutMapping("/admin")
    public User updateAdmin(@RequestBody String Username) {
        User user = userRepository.findByName(Username)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
      
        user.setRole(Role.ROLE_ADMIN);
      
        return user;
    }
}
