package digital.shraddha.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

	USER_NOT_FOUND("UserEntity not found", HttpStatus.NOT_FOUND),
	USER_ALREADY_EXISTS("UserEntity already exists", HttpStatus.CONFLICT),
	INVALID_USER_DATA("Invalid user data", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED_ACCESS("Unauthorized access", HttpStatus.UNAUTHORIZED),
	FORBIDDEN_ACTION("Forbidden action", HttpStatus.FORBIDDEN),
	USER_ALREADY_FOLLOWS("User already follows the target user", HttpStatus.CONFLICT),
	USER_NOT_FOLLOWING("User is not following the target user", HttpStatus.CONFLICT),
	NO_FOLLOWERS("User has no followers", HttpStatus.NOT_FOUND),
	NO_FOLLOWING("User is not following anyone", HttpStatus.NOT_FOUND),

	INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String message;
	private final HttpStatus httpStatus;

	ErrorType(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return this.name();
	}
}
