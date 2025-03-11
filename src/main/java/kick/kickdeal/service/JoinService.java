package kick.kickdeal.service;

import kick.kickdeal.dto.JoinDTO;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public String joinprocess(JoinDTO dto) {
        if (userRepository.existsById(dto.getId())) {
            return "fail";
        }

        User user = new User();
        user.setId(dto.getId());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return "ok";
    }
}
