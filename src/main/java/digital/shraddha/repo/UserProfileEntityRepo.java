package digital.shraddha.repo;

import digital.shraddha.model.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileEntityRepo extends JpaRepository<UserProfileEntity, UUID> {
}
