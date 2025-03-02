package kick.kickdeal.service;

import kick.kickdeal.dto.CustomUserDetails;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        //DB에서 조회
        User userData = userRepository.findById(id);

        if (userData != null){

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        } else {
            // 사용자 없음 예외 던지기
            throw new UsernameNotFoundException("User not found with username: " + id);
        }
    }
}
