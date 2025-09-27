package digital.shraddha.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "user_id", nullable = false, unique = true)
	private UserEntity user;

	@Column (length = 100)
	private String fullName;

	@Column (length = 50)
	private String displayName;

	@Column (length = 255)
	private String bio;

	@Builder.Default
	@OneToMany (
			mappedBy = "profile",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER
	)
	private List<UserProfileMediaEntity> mediaList = new ArrayList<>();
}
