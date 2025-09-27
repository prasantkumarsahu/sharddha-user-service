package digital.shraddha.exception;

import digital.shraddha.model.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler (ApiException.class)
	public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
		String traceId = generateTraceId();
		log.error("[{}] API exception: {}", traceId, ex.getMessage(), ex);

		return ResponseEntity.status(ex.getHttpStatus())
				.body(buildErrorResponse(
						ex.getHttpStatus().value(),
						ex.getCode(),
						ex.getMessage(),
						request.getRequestURI(),
						traceId
				));
	}

	@ExceptionHandler (Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
		String traceId = generateTraceId();
		log.error("[{}] Unexpected exception", traceId, ex);

		return ResponseEntity.status(500)
				.body(buildErrorResponse(
						500,
						"INTERNAL_SERVER_ERROR",
						"An unexpected error occurred. Please try again later.",
						request.getRequestURI(),
						traceId
				));
	}

	private ErrorResponse buildErrorResponse(int status, String error, String message, String path, String traceId) {
		return new ErrorResponse(
				java.time.LocalDateTime.now(),
				status,
				error,
				message,
				path,
				traceId
		);
	}

	private String generateTraceId() {
		return UUID.randomUUID().toString();
	}
}
