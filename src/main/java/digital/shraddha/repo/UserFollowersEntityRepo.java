package digital.shraddha.repo;

import digital.shraddha.model.entity.UserFollowersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFollowersEntityRepo extends JpaRepository<UserFollowersEntity, UUID> {
}
