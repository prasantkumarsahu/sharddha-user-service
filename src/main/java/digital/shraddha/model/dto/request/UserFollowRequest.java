package digital.shraddha.model.dto.request;

import java.util.UUID;

public record UserFollowRequest(
		UUID followerId,
		UUID followeeId
) {
}
