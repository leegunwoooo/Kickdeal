package kick.kickdeal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String id;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    public void update(String id){
        this.id = id;
    }
}

