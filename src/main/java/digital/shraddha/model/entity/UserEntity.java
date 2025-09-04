package digital.shraddha.model.entity;

import digital.shraddha.model.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "users", indexes = {
		@Index (name = "idx_users_username", columnList = "username"),
		@Index (name = "idx_users_email", columnList = "email")
}, uniqueConstraints = {
		@UniqueConstraint (name = "uk_users_username", columnNames = "username"),
		@UniqueConstraint (name = "uk_users_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners (AuditingEntityListener.class)
@SQLRestriction ("status <> 'DELETED'")
public class UserEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;

	@Column (nullable = false, unique = true, length = 50)
	private String username;

	@Column (nullable = false, unique = true, length = 100)
	private String email;

	@Column (length = 20, unique = true)
	private String phone;

	@Enumerated (EnumType.STRING)
	@Column (nullable = false, length = 20)
	@Builder.Default
	private AccountStatus status = AccountStatus.ACTIVE;

	@CreatedDate
	@Column (nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@PrePersist
	@PreUpdate
	public void normalize() {
		if (username != null) username = username.toLowerCase();
		if (email != null) email = email.toLowerCase();
	}
}
