package digital.shraddha.controller;

import digital.shraddha.model.dto.UserDto;
import digital.shraddha.model.dto.request.UserFollowRequest;
import digital.shraddha.model.entity.UserFollowersEntity;
import digital.shraddha.service.UserFollowersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping ("/users/followers")
@RequiredArgsConstructor
@Tag (name = "User Followers Management", description = "Manage user followers and following relationships")
public class UserFollowersController {

	private final UserFollowersService userFollowersService;

	@PostMapping ("/follow")
	public ResponseEntity<UserFollowersEntity> followUser(@RequestBody UserFollowRequest request) {
		log.info("User {} is following User {}", request.followerId(), request.followeeId());
		UserFollowersEntity followed = userFollowersService.followUser(request);
		return ResponseEntity.ok(followed);
	}

	@GetMapping ("/followers")
	public ResponseEntity<Page<UserDto>> getFollowers(@RequestParam UUID userId,
													  @RequestParam int page,
													  @RequestParam int size) {
		log.info("Fetching followers");
		Page<UserDto> followers = userFollowersService.getFollowers(userId, page, size);
		return ResponseEntity.ok(followers);
	}

	@GetMapping ("/following")
	public ResponseEntity<Page<UserDto>> getFollowing(@RequestParam UUID userId,
													  @RequestParam int page,
													  @RequestParam int size) {
		log.info("Fetching following");
		Page<UserDto> following = userFollowersService.getFollowing(userId, page, size);
		return ResponseEntity.ok(following);
	}

	@DeleteMapping ("/unfollow")
	public ResponseEntity<Void> unfollowUser(@RequestParam UUID followerId,
											 @RequestParam UUID followeeId) {
		log.info("User {} is unfollowing User {}", followerId, followeeId);
		userFollowersService.unfollowUser(followerId, followeeId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping ("/suggestions")
	public ResponseEntity<Page<UserDto>> suggestUsersToFollow(@RequestParam UUID userId,
															  @RequestParam int limit) {
		log.info("Fetching user follow suggestions");
		Page<UserDto> suggestions = userFollowersService.suggestUsersToFollow(userId, limit);
		return ResponseEntity.ok(suggestions);
	}
}
