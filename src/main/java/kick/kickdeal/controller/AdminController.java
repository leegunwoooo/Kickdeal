package kick.kickdeal.controller;

import kick.kickdeal.dto.UpdateAdminDTO;
import kick.kickdeal.entity.Role;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @PutMapping("/admin")
    public User updateAdmin(@RequestBody UpdateAdminDTO dto) {
        User user = userRepository.findById(dto.getUsername());

        if (user == null) {
            throw new RuntimeException("없는 유저입니다.");
        }

        user.setRole(Role.ROLE_ADMIN);

        return userRepository.save(user);
    }
}
