package uz.akbar.edu_center_kaizen.payload.request;

import uz.akbar.edu_center_kaizen.enums.GroupStatus;

public record GroupUpdateDto(
		String name, // e.g., "Java-101", "Spring-Advanced"
		String description,
		GroupStatus groupStatus,
		String subject,
		Long teacherId) {
}
