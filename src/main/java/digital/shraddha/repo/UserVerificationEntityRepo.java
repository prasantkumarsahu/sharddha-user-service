package digital.shraddha.repo;

import digital.shraddha.model.entity.UserVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserVerificationEntityRepo extends JpaRepository<UserVerificationEntity, UUID> {
}
