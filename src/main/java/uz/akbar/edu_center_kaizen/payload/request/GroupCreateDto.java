package uz.akbar.edu_center_kaizen.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GroupCreateDto(
		@NotBlank(message = "name is required") String name, // e.g., "Java-101", "Spring-Advanced"
		String description,
		@NotBlank(message = "subject is required") String subject,
		@NotNull(message = "teacher id id required") Long teacherId) {
}
