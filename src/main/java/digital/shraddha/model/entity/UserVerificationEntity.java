package digital.shraddha.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (
		name = "user_verifications",
		indexes = {
				@Index (name = "idx_user_verification_user_id", columnList = "user_id")
		}
)
@EntityListeners (AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVerificationEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "user_id", nullable = false, unique = true)
	private UserEntity user;

	@Column (nullable = false)
	@Builder.Default
	private boolean emailVerified = false;

	@Column (nullable = false)
	@Builder.Default
	private boolean phoneVerified = false;

	@Column (nullable = false)
	@Builder.Default
	private boolean verifiedBadge = false; // Blue tick

	@CreatedDate
	@Column (updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column (nullable = false)
	private LocalDateTime updatedAt;
}
