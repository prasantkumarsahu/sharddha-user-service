package digital.shraddha.repo;

import digital.shraddha.model.entity.UserProfileMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileMediaEntityRepo extends JpaRepository<UserProfileMediaEntity, UUID> {
}
