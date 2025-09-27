package digital.shraddha.repo;

import digital.shraddha.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByPhone(String phone);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	@Query ("SELECT u FROM UserEntity u WHERE u.id NOT IN :followingIds")
	Page<UserEntity> findAllNotInFollowing(@Param ("followingIds") List<UUID> followingIds, Pageable pageable);
}
