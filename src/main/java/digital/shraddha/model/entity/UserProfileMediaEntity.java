package digital.shraddha.model.entity;

import digital.shraddha.model.enums.ProfileMediaType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table (
		name = "user_profile_media",
		uniqueConstraints = {
				@UniqueConstraint (
						name = "uk_profile_media_type",
						columnNames = {"profile_id", "media_type"}
				)
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileMediaEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne (optional = false)
	@JoinColumn (name = "profile_id", nullable = false)
	private UserProfileEntity profile;

	@Enumerated (EnumType.STRING)
	@Column (nullable = false, length = 20)
	private ProfileMediaType mediaType;

	@Column (nullable = false, length = 255)
	private String url;
}
