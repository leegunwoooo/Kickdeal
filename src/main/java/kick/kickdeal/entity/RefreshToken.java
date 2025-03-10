package kick.kickdeal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String refresh;

    @Column(nullable = false)
    private LocalDateTime expiration;

    public RefreshToken(String username, String refresh, LocalDateTime expiration) {
        this.username = username;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
