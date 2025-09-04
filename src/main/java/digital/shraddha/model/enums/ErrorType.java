package digital.shraddha.model.enums;

import org.springframework.http.HttpStatus;

public enum ErrorType {

	// UserEntity service specific errors
	USER_NOT_FOUND("UserEntity not found", HttpStatus.NOT_FOUND),
	USER_ALREADY_EXISTS("UserEntity already exists", HttpStatus.CONFLICT),
	INVALID_USER_DATA("Invalid user data", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED_ACCESS("Unauthorized access", HttpStatus.UNAUTHORIZED),
	FORBIDDEN_ACTION("Forbidden action", HttpStatus.FORBIDDEN),

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

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
