package uz.akbar.edu_center_kaizen.payload.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AppResponse(
		boolean success,
		String message,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime timestamp,
		Object data) {

	// Static factory method with automatic timestamp
	public static AppResponse of(boolean success, String message, Object data) {
		return new AppResponse(success, message, LocalDateTime.now(), data);
	}

	// Static factory method for success responses
	public static AppResponse success(String message, Object data) {
		return new AppResponse(true, message, LocalDateTime.now(), data);
	}

	// Static factory method for error responses
	public static AppResponse error(String message) {
		return new AppResponse(false, message, LocalDateTime.now(), null);
	}
}
