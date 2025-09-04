package digital.shraddha.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (
		name = "user_follows",
		uniqueConstraints = {
				@UniqueConstraint (
						name = "uk_user_follow",
						columnNames = {"follower_id", "followee_id"}
				)
		},
		indexes = {
				@Index (name = "idx_follower_id", columnList = "follower_id"),
				@Index (name = "idx_followee_id", columnList = "followee_id")
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners (AuditingEntityListener.class)
public class UserFollowersEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;

	// Who is following
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "follower_id", nullable = false, foreignKey = @ForeignKey (name = "fk_follower"))
	private UserEntity follower;

	// Who is being followed
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "followee_id", nullable = false, foreignKey = @ForeignKey (name = "fk_followee"))
	private UserEntity followee;

	@CreatedDate
	@Column (nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	@PreUpdate
	private void validateFollow() {
		if (follower.getId().equals(followee.getId())) {
			throw new IllegalStateException("A user cannot follow themselves");
		}
	}
}
