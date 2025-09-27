package digital.shraddha.service;

import digital.shraddha.model.dto.UserDto;
import digital.shraddha.model.dto.request.UserFollowRequest;
import digital.shraddha.model.entity.UserEntity;
import digital.shraddha.model.entity.UserFollowersEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserFollowersService {

	UserFollowersEntity followUser(UserFollowRequest request);

	void unfollowUser(UUID followerId, UUID followeeId);

	boolean isFollowing(UUID followerId, UUID followeeId);

	Page<UserDto> getFollowers(UUID userId, int page, int size);

	Page<UserDto> getFollowing(UUID userId, int page, int size);

	long countFollowers(UUID userId);

	long countFollowing(UUID userId);

	Page<UserDto> suggestUsersToFollow(UUID userId, int limit);
}
