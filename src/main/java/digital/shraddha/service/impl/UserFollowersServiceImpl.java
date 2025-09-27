package digital.shraddha.service.impl;

import digital.shraddha.exception.ApiException;
import digital.shraddha.mapper.UserMapper;
import digital.shraddha.model.dto.UserDto;
import digital.shraddha.model.dto.request.UserFollowRequest;
import digital.shraddha.model.entity.UserEntity;
import digital.shraddha.model.entity.UserFollowersEntity;
import digital.shraddha.model.enums.ErrorType;
import digital.shraddha.repo.UserEntityRepo;
import digital.shraddha.repo.UserFollowersEntityRepo;
import digital.shraddha.service.UserFollowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFollowersServiceImpl implements UserFollowersService {

	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int MAX_PAGE_SIZE = 30;
	private final UserEntityRepo userEntityRepo;
	private final UserFollowersEntityRepo userFollowersEntityRepo;
	private final UserMapper userMapper;

	@Override
	public UserFollowersEntity followUser(UserFollowRequest request) {
		UserEntity follower = userEntityRepo.findById(request.followerId())
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		UserEntity followee = userEntityRepo.findById(request.followeeId())
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		if (userFollowersEntityRepo.existsByFollowerAndFollowee(follower, followee)) {
			throw new ApiException(ErrorType.USER_ALREADY_FOLLOWS);
		}

		UserFollowersEntity entity = UserFollowersEntity.builder()
				.follower(follower)
				.followee(followee)
				.build();

		return userFollowersEntityRepo.save(entity);
	}

	@Override
	public void unfollowUser(UUID followerId, UUID followeeId) {
		UserEntity follower = userEntityRepo.findById(followerId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		UserEntity followee = userEntityRepo.findById(followeeId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		userFollowersEntityRepo.findByFollowerAndFollowee(follower, followee)
				.ifPresentOrElse(
						userFollowersEntityRepo :: delete,
						() -> {
							throw new ApiException(ErrorType.USER_NOT_FOLLOWING);
						}
				);
	}

	@Override
	public boolean isFollowing(UUID followerId, UUID followeeId) {
		UserEntity follower = userEntityRepo.findById(followerId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		UserEntity followee = userEntityRepo.findById(followeeId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		return userFollowersEntityRepo.existsByFollowerAndFollowee(follower, followee);
	}

	@Override
	public Page<UserDto> getFollowers(UUID userId, int page, int size) {
		if (page < 0) page = 0;
		if (size <= 0 || size > MAX_PAGE_SIZE) size = DEFAULT_PAGE_SIZE;

		UserEntity user = userEntityRepo.findById(userId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		return userFollowersEntityRepo.findAllByFollowee(user, pageable)
				.map(userMapper :: toDto);
	}

	@Override
	public Page<UserDto> getFollowing(UUID userId, int page, int size) {
		if (page < 0) page = 0;
		if (size <= 0 || size > MAX_PAGE_SIZE) size = DEFAULT_PAGE_SIZE;

		UserEntity user = userEntityRepo.findById(userId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		return userFollowersEntityRepo.findAllByFollower(user, pageable)
				.map(userMapper :: toDto);
	}

	@Override
	public long countFollowers(UUID userId) {
		UserEntity user = userEntityRepo.findById(userId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
		return userFollowersEntityRepo.countByFollowee(user);
	}

	@Override
	public long countFollowing(UUID userId) {
		UserEntity user = userEntityRepo.findById(userId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
		return userFollowersEntityRepo.countByFollower(user);
	}

	@Override
	public Page<UserDto> suggestUsersToFollow(UUID userId, int limit) {
		UserEntity user = userEntityRepo.findById(userId)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

		if (limit <= 0 || limit > 20) limit = 10;

		List<UUID> followingIds = userFollowersEntityRepo.findAllFolloweeIdsByFollower(user);

		followingIds.add(user.getId());

		Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
		return userEntityRepo.findAllNotInFollowing(followingIds, pageable)
				.map(userMapper :: toDto);
	}
}
