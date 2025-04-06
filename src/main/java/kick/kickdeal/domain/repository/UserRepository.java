package kick.kickdeal.domain.repository;

import kick.kickdeal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsById(String id);

    User findById(String id);
}
