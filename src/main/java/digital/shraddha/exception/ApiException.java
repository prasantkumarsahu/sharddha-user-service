package digital.shraddha.exception;

import digital.shraddha.model.enums.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

	private final ErrorType errorType;

	/**
	 * Use enum message as default
	 */
	public ApiException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}

	/**
	 * Optional: override message if you want a more specific detail
	 */
	public ApiException(ErrorType errorType, String customMessage) {
		super(customMessage != null ? customMessage : errorType.getMessage());
		this.errorType = errorType;
	}

	/**
	 * Convenience method to get HTTP status
	 */
	public HttpStatus getHttpStatus() {
		return errorType.getHttpStatus();
	}

	/**
	 * Convenience method to get machine-friendly code
	 */
	public String getCode() {
		return errorType.getCode();
	}
}
