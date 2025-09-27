package digital.shraddha.repo;

import digital.shraddha.model.entity.UserEntity;
import digital.shraddha.model.entity.UserFollowersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFollowersEntityRepo extends JpaRepository<UserFollowersEntity, UUID> {

	boolean existsByFollowerAndFollowee(UserEntity follower, UserEntity followee);

	Optional<UserFollowersEntity> findByFollowerAndFollowee(UserEntity follower, UserEntity followee);

	@Query ("SELECT f.followee FROM UserFollowersEntity f WHERE f.follower = :follower")
	Page<UserEntity> findAllByFollower(UserEntity follower, Pageable pageable);

	@Query ("SELECT f.follower FROM UserFollowersEntity f WHERE f.followee = :followee")
	Page<UserEntity> findAllByFollowee(UserEntity followee, Pageable pageable);

	@Query ("SELECT f.followee.id FROM UserFollowersEntity f WHERE f.follower = :follower")
	List<UUID> findAllFolloweeIdsByFollower(UserEntity follower);

	@Query ("SELECT f.follower.id FROM UserFollowersEntity f WHERE f.followee = :followee")
	List<UUID> findAllFollowerIdsByFollowee(UserEntity followee);

	long countByFollowee(UserEntity followee);

	long countByFollower(UserEntity follower);

	void deleteByFollowerAndFollowee(UserEntity follower, UserEntity followee);
}
