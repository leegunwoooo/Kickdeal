package kick.kickdeal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String id;

    private String password;

    private String token;
}
